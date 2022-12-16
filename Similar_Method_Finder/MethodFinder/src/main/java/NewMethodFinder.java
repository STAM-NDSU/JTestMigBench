import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.github.javaparser.symbolsolver.utils.SymbolSolverCollectionStrategy;
import com.github.javaparser.utils.ProjectRoot;
import com.github.javaparser.utils.SourceRoot;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class NewMethodFinder {
    static double thresholdValue = 0.75;
    int count = 0;
    String srcDir = "D:\\Lib_Projects\\json\\gson-master";
    String targetDir = "D:\\Lib_Projects\\json\\genson-master";
    List<String> discardedMethods = new ArrayList<>(Arrays.asList("equals", "hashCode", "clone", "toString"));

    public static void main(String[] args){
        new NewMethodFinder().calculateSimilarity();
    }

    private void calculateSimilarity(){
        Word2Vec vec = WordVectorSerializer.readWord2VecModel("word2vec_model");
        //Word2Vec googleVec = WordVectorSerializer.readWord2VecModel(new File("D:\\GoogleNews-vectors-negative300.bin"));
        //System.out.println("size-length: "+calculateVecSimilarity("size", "length", googleVec));
        //System.out.println("put-add: "+calculateVecSimilarity("put", "add", googleVec));
        
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

        ProjectRoot sourceProjectRoot = new SymbolSolverCollectionStrategy().collect(new File(srcDir).toPath());
        for(SourceRoot sourceRoot: sourceProjectRoot.getSourceRoots()){
            try {
                sourceRoot.tryToParse();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            for(CompilationUnit compilationUnit: sourceRoot.getCompilationUnits()){
                compilationUnit.accept(new VoidVisitorAdapter<Object>() {
                    @Override
                    public void visit(MethodDeclaration declaration, Object arg){
                        super.visit(declaration, arg);
                        String sourceClassName = declaration.findCompilationUnit().get().getType(0).getNameAsString();
                        if(!sourceClassName.endsWith("Test") && !discardedMethods.contains(declaration.getNameAsString())
                                && isNotDeclaredInInterfaceAndAbstract(declaration)){
                            calculateSimFilterApproach(vec, levenshteinDistance, declaration, sourceClassName);
                        }
                    }
                }, null);
            }
        }
        System.out.println(count);
    }

    private boolean hasTest(MethodDeclaration declaration, String className, String rootDir){
        String methodName = declaration.getNameAsString();
        String path = findFileOrDir(new File(rootDir), className+"Test.java");
        if(path != null){
            final boolean[] testPresent = {false};
            getCompilationUnit(new File(path)).accept(new VoidVisitorAdapter<Object>() {
                @Override
                public void visit(MethodDeclaration node, Object arg){
                    super.visit(node, arg);
                    if(isTestMethod(node)){
                        node.accept(new VoidVisitorAdapter<Object>() {
                            @Override
                            public void visit(MethodCallExpr callExpr, Object arg){
                                super.visit(callExpr, arg);
                                if(callExpr.getNameAsString().equals(methodName)){
                                    testPresent[0] = true;
                                }
                            }
                        }, null);
                    }
                }
            }, null);
            return testPresent[0];
        }
        return false;
    }

    private void calculateSimFilterApproach(Word2Vec vec, LevenshteinDistance levenshteinDistance, MethodDeclaration sourceMethod, String sourceClass) {
        double finalScore = 0, finalClassScore = 0;
        MethodDeclaration target = null;
        File targetFile = null;

        Stack<File> stack = getTargetFilesStack();
        while(!stack.isEmpty()) {
            File child = stack.pop();
            if (child.isDirectory()) {
                for(File f : Objects.requireNonNull(child.listFiles())) stack.push(f);
            } else if (isJavaFile(child)) {
                String name = FilenameUtils.removeExtension(child.getName());
                try {
                    for(MethodDeclaration targetMethod: getMethodDecls(new JavaParser().parse(child).getResult().get())){
                        double score = calculateVecSimilarity(sourceMethod, targetMethod, vec);
                        double vecClassScore = calculateVecSimilarity(sourceClass, name, vec);
                        double levClassScore = calculateLVSim(levenshteinDistance, sourceClass, name);

                        if(score>finalScore){
                            finalScore = score;
                            finalClassScore = vecClassScore;
                            target = targetMethod;
                            targetFile = child;
                        }else if(score == finalScore && target != null){
                            //resolve with Levenshtein
                            double score1 = calculateLVSim(levenshteinDistance, sourceMethod, target);
                            double score2 = calculateLVSim(levenshteinDistance, sourceMethod, targetMethod);
                            if(score2>score1){
                                finalClassScore = vecClassScore;
                                target = targetMethod;
                                targetFile = child;
                            }else if(score1 == score2){
                                if(vecClassScore>finalClassScore){
                                    targetFile = child;
                                    target = targetMethod;
                                    finalClassScore = vecClassScore;
                                }else if(vecClassScore == finalClassScore){
                                    double levClassScore2 = calculateLVSim(levenshteinDistance, sourceClass, FilenameUtils.removeExtension(targetFile.getName()));
                                    if(levClassScore>levClassScore2){
                                        targetFile = child;
                                        target = targetMethod;
                                    }
                                }
                            }
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        if(target != null){
            checkThresholdAndStoreSimMethods(finalScore, sourceMethod, target, sourceClass, targetFile);
        }
    }

    private void calculateSimWeightedApproach(Word2Vec vec, LevenshteinDistance levenshteinDistance, MethodDeclaration sourceMethod, String sourceClass){
        double finalScore = 0;
        MethodDeclaration target = null;
        File targetFile = null;

        Stack<File> stack = getTargetFilesStack();
        while(!stack.isEmpty()) {
            File child = stack.pop();
            if (child.isDirectory()) {
                for(File f : Objects.requireNonNull(child.listFiles())) stack.push(f);
            } else if (isJavaFile(child)) {
                String name = FilenameUtils.removeExtension(child.getName());
                try {
                    double classSimScore = (calculateLVSim(levenshteinDistance, sourceClass, name)+calculateVecSimilarity(sourceClass, name, vec))/2;
                    for(MethodDeclaration targetMethod: getMethodDecls(new JavaParser().parse(child).getResult().get())){
                        double methodSimScore = (calculateVecSimilarity(sourceMethod, targetMethod, vec)+calculateLVSim(levenshteinDistance, sourceMethod, targetMethod))/2;
                        double weightedScore = (0.75*methodSimScore)+(0.25*classSimScore);

                        if(weightedScore>finalScore){
                            finalScore = weightedScore;
                            target = targetMethod;
                            targetFile = child;
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        if(target != null){
            checkThresholdAndStoreSimMethods(finalScore, sourceMethod, target, sourceClass, targetFile);
        }
    }

    private boolean isJavaFile(File file){
        return file.isFile() && FilenameUtils.getExtension(file.getName()).equals("java")
                && !FilenameUtils.removeExtension(file.getName()).endsWith("Test") && file.getAbsolutePath().contains("\\src\\");
    }

    private Stack<File> getTargetFilesStack(){
        Stack<File> stack = new Stack<>();
        stack.push(new File(targetDir));
        return stack;
    }

    private void checkThresholdAndStoreSimMethods(double finalScore, MethodDeclaration sourceMethod, MethodDeclaration target, String sourceClass, File targetFile){
        String targteClass = target.getNameAsString()+":"+target.findCompilationUnit().get().getType(0).getNameAsString();
        //String testPresent = (hasTest(sourceMethod, sourceClass, srcDir) || hasTest(target, targteClass, targetDir)) ? "yes" : "no";
        if(finalScore>thresholdValue && isNotDeclaredInInterfaceAndAbstract(target)
                && (hasTest(sourceMethod, sourceClass, srcDir) || hasTest(target, targteClass, targetDir))){
            System.out.println("-> "+sourceMethod.getNameAsString()+":"+sourceClass+" - "+targteClass+" - "+finalScore);
            //System.out.println("-> "+sourceMethod.getNameAsString()+":"+sourceClass+" - "+targteClass+" - "+finalScore+" : Test present - "+testPresent);
            count++;
        }
    }

    private boolean isNotDeclaredInInterfaceAndAbstract(MethodDeclaration declaration){
        if(declaration.findCompilationUnit().isPresent() && declaration.findCompilationUnit().get().getType(0).isClassOrInterfaceDeclaration()){
            return !(declaration.findCompilationUnit().get().getType(0).asClassOrInterfaceDeclaration().isInterface()
                    || declaration.findCompilationUnit().get().getType(0).getModifiers().contains(Modifier.abstractModifier()));
        }
        return true;
    }

    private double calculateVecSimilarity(MethodDeclaration sourceMethod, MethodDeclaration targetMethod, Word2Vec vec){
        ArrayList<String> sourceWords = splitCamelCase(sourceMethod.getNameAsString());
        ArrayList<String> targetWords = splitCamelCase(targetMethod.getNameAsString());

        double totalScore = 0.0;
        for(String sourceWord: sourceWords){
            double score = 0.0;
            for(String targetWord: targetWords){
                double tempScore = vec.similarity(sourceWord, targetWord);
                if(tempScore>score)
                    score = tempScore;
            }
            totalScore += score;
        }
        return totalScore/sourceWords.size();
    }

    private double calculateVecSimilarity(String sourceMethod, String targetMethod, Word2Vec vec){
        ArrayList<String> sourceWords = splitCamelCase(sourceMethod);
        ArrayList<String> targetWords = splitCamelCase(targetMethod);

        double totalScore = 0.0;
        for(String sourceWord: sourceWords){
            double score = 0.0;
            for(String targetWord: targetWords){
                double tempScore = vec.similarity(sourceWord, targetWord);
                if(tempScore>score)
                    score = tempScore;
            }
            totalScore += score;
        }
        return totalScore/sourceWords.size();
    }

    private double calculateLVSim(LevenshteinDistance levenshteinDistance, MethodDeclaration sourceMethod,
                                  MethodDeclaration targetMethod){
        ArrayList<String> source, target;
        if(!sourceMethod.getNameAsString().equals(targetMethod.getNameAsString())){
            source = splitCamelCase(sourceMethod.getNameAsString());
            target = splitCamelCase(targetMethod.getNameAsString());
            splitWordFurther(source, target);
            removeDuplicates(source);
            removeDuplicates(target);
            removeMatchingWords(source, target);
        }else{
            source = new ArrayList<>();
            source.add(sourceMethod.getNameAsString());
            target = new ArrayList<>();
            target.add(targetMethod.getNameAsString());
        }

        String sourceMethodName = buildName(source);
        String targetMethodName = buildName(target);
        double maxLength;
        if(sourceMethod.getNameAsString().length() == targetMethod.getNameAsString().length()
                || sourceMethod.getNameAsString().length()>targetMethod.getNameAsString().length()){
            maxLength = sourceMethod.getNameAsString().length();
        }else{
            maxLength = targetMethod.getNameAsString().length();
        }
        double returnScore = 0.0;
        String sourceReturnType = getReturnType(sourceMethod);
        String targetReturnType = getReturnType(targetMethod);
        if(sourceReturnType.equals(targetReturnType))
            returnScore = 1.0;

        double paramScore = getParamSimilarityScore(sourceMethod, targetMethod);

        double distance = 1 - ((double)levenshteinDistance.apply(sourceMethodName, targetMethodName)/maxLength);
        return  (distance*0.8)+(returnScore*0.1)+(paramScore*0.1);
    }

    private double calculateLVSim(LevenshteinDistance levenshteinDistance, String sourceClass, String targetClass){
        ArrayList<String> source, target;
        if(!sourceClass.equals(targetClass)){
            source = splitCamelCase(sourceClass);
            target = splitCamelCase(targetClass);
            splitWordFurther(source, target);
            removeDuplicates(source);
            removeDuplicates(target);
            removeMatchingWords(source, target);
        }else{
            source = new ArrayList<>();
            source.add(sourceClass);
            target = new ArrayList<>();
            target.add(targetClass);
        }

        String sourceClassName = buildName(source);
        String targetClassName = buildName(target);
        double maxLength;
        if(sourceClass.length() == targetClass.length() || sourceClass.length()>targetClass.length()){
            maxLength = sourceClass.length();
        }else{
            maxLength = targetClass.length();
        }

        return  1 - ((double)levenshteinDistance.apply(sourceClassName, targetClassName)/maxLength);
    }

    private double getParamSimilarityScore(MethodDeclaration sourceMethod, MethodDeclaration targetMethod){
        double paramScore;
        ArrayList<String> sourceParams = getParams(sourceMethod);
        ArrayList<String> targetParams = getParams(targetMethod);

        if(sourceParams.isEmpty() && targetParams.isEmpty()){
            paramScore = 1.0;
        }else{
            //divide equal weight among parameters
            double matchCount = 0;
            if(sourceParams.size() == targetParams.size() || sourceParams.size()>targetParams.size()){
                for(String type: targetParams){
                    if(sourceParams.contains(type)){
                        matchCount++;
                    }
                }
                paramScore = matchCount/sourceParams.size();
            }else{
                for(String type: sourceParams){
                    if(targetParams.contains(type)){
                        matchCount++;
                    }
                }
                paramScore = matchCount/targetParams.size();
            }
        }
        return paramScore;
    }

    private ArrayList<String> getParams(MethodDeclaration node){
        ArrayList<String> params = new ArrayList<>();
        for(Parameter parameter: node.getParameters()){
            String type = parameter.getTypeAsString();
            if(type.contains("Set") || type.contains("List") || type.contains("Map") || type.contains("Vector"))
                type = "Collection";
            params.add(type);
        }
        return params;
    }

    private String getReturnType(MethodDeclaration node){
        String type = node.getTypeAsString();
        if(type.contains("Set") || type.contains("List") || type.contains("Map") || type.contains("Vector"))
            type = "Collection";
        return type;
    }

    String buildName(ArrayList<String> words){
        StringBuilder name = new StringBuilder();
        for(String word: words){
            name.append(word);
        }
        return name.toString();
    }

    void removeMatchingWords(ArrayList<String> source, ArrayList<String> target){
        ArrayList<String> matchedWords = new ArrayList<>();
        for(String word: source){
            if(target.contains(word))
                matchedWords.add(word);
        }
        for(String word: matchedWords){
            source.remove(word);
            target.remove(word);
        }
    }

    void removeDuplicates(ArrayList<String> words){
        ArrayList<String> uniqueWords = new ArrayList<>();
        for(String word: words){
            if(!uniqueWords.contains(word))
                uniqueWords.add(word);
        }
        words.clear();
        words.addAll(uniqueWords);
    }

    void splitWordFurther(ArrayList<String> source, ArrayList<String> target){
        ArrayList<String> newTargetWords = new ArrayList<>();
        ArrayList<String> removeFromTarget = new ArrayList<>();
        ArrayList<String> newSourceWords = new ArrayList<>();
        ArrayList<String> removeFromSource = new ArrayList<>();

        for(String sourceWord: source){
            for(String targetWord: target){
                if(targetWord.contains(sourceWord)){
                    removeFromTarget.add(targetWord);
                    newTargetWords.add(targetWord.replace(sourceWord, ""));
                    newTargetWords.add(sourceWord);
                }
            }
        }

        for(String targetWord: target){
            for(String sourceWord: source){
                if(sourceWord.contains(targetWord)){
                    removeFromSource.add(sourceWord);
                    newSourceWords.add(sourceWord.replace(targetWord, ""));
                    newSourceWords.add(targetWord);
                }
            }
        }

        source.removeAll(removeFromSource);
        source.addAll(newSourceWords);
        target.removeAll(removeFromTarget);
        target.addAll(newTargetWords);

    }

    ArrayList<String> splitCamelCase(String name){
        ArrayList<String> words = new ArrayList<>();
        for(String word: name.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")){
            if(!words.contains(word.toLowerCase()))
                words.add(word.toLowerCase());
        }
        return words;
    }

    private boolean hasVisibleForTestingAnnotation(MethodDeclaration node){
        for(AnnotationExpr annotationExpr: node.getAnnotations()){
            if(annotationExpr.getNameAsString().equals("VisibleForTesting"))
                return true;
        }
        return false;
    }


    private ArrayList<MethodDeclaration> getMethodDecls(CompilationUnit cu){
        ArrayList<MethodDeclaration> methodSign = new ArrayList<>();
        cu.accept(new VoidVisitorAdapter<Object>() {
            @Override
            public void visit(MethodDeclaration node, Object arg){
                super.visit(node, arg);
                if(!discardedMethods.contains(node.getNameAsString()) && (!node.isPrivate() || hasVisibleForTestingAnnotation(node))){
                    methodSign.add(node);
                }
            }
        }, null);
        return methodSign;
    }

    private String findFileOrDir(File root, String name) {
        if (root.getName().equals(name)) return root.getAbsolutePath();
        File[] files = root.listFiles();
        if(files != null) {
            for (File file : files) {
                if(file.isDirectory()) {
                    String path = findFileOrDir(file, name);
                    if (path != null) return path;
                } else if(file.getName().equals(name)){
                    return file.getAbsolutePath();
                }
            }
        }
        return null;
    }

    private CompilationUnit getCompilationUnit(File file) {

        CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
        combinedTypeSolver.add(new ReflectionTypeSolver());

        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(combinedTypeSolver);
        ParserConfiguration parserConfiguration = new ParserConfiguration();
        parserConfiguration.setSymbolResolver(symbolSolver);
        JavaParser parser = new JavaParser(parserConfiguration);

        CompilationUnit cu = null;
        try {
            cu = parser.parse(file).getResult().get();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return cu;
    }

    private boolean isTestMethod(MethodDeclaration node){
        boolean testMethod = false;
        if(node.getNameAsString().startsWith("test")){
            testMethod = true;
        }else{
            for(AnnotationExpr expr: node.getAnnotations()){
                if(expr.getNameAsString().equals("Test")){
                    testMethod = true;
                }
            }
        }
        return testMethod;
    }


}
