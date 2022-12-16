
# JTestMigBench and JTestMigTax

The artifact repository for the paper titled:
>Ajay Kumar Jha, Mohayeminul Islam, and Sarah Nadi. JTestMigBench and JTestMigTax: A benchmark and taxonomy for unit test migration. 
>In <i>Proceedings of 30th IEEE International Conference on Software Analysis, Evolution and Reengineering (SANER)</i>. 2023. ERA Track

In this paper, we create a benchmark of 510 manually migrated or reused JUnit tests for 186 methods from 5 popular libraries. We also provide a taxonomy of code transformation patterns. 

## Contents

Following artifacts are included for reproducibility:
1) **`Study_Subjects`:** This folder includes a sheet that contains similar method pairs found by our tool (SMFinder) in 5 different JSON and common utilities libraries. The sheet also contains 147 manually validated method pairs where a method in a pair has unit tests.  
2) **`Similar_Method_Finder`:** This folder includes our SMFinder tool used to find similar methods in the 5 different libraries. 
Specific versions of the libraries used to find similar methods are included in a sheet named `Libraries`.
3) **`Benchmark_Migrated_Tests`:** This folder includes the following contents:
   - A sheet named `Manual_Test_Migration_Results_Summary`, which includes the results we received from our test migrators.
   - A sheet named `Original_or_Source_Unit_Tests`, which includes GitHub links for the 510 original or source unit tests that we successfully migrated. 
   - A folder named `Manually_Migrated_Tests`, which includes 510 manually migrated tests categorized by library pairs. 
   - A sheet named `Code_Coverage_Manually_Migrated_Tests`, which includes code coverage data of the manually migrated tests.


## Reproducing the results
<pre>Note that all the evaluation was performed on Windows 10.</pre>

### Finding similar method pairs:
1) **`Setup tool:`** download SMFinder (`/Similar_Method_Finder/MethodFinder`). 
2) **`Clone libraries:`** clone the specific versions of the libraries specified in `/Similar_Method_Finder/Libraries` sheet.
3) **`Provide input:`** provide source and target libraries' path to `srcDir` and `targetDir` fields in the `/Similar_Method_Finder/MethodFinder/NewMethodFinder.java` file.
4) **`Getting similar methods:`** run the tool to get similar methods on the output console.   

