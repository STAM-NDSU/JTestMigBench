package com.alibaba.fastjson;

import junit.framework.TestCase;

import java.lang.reflect.Type;
import java.util.List;

public class TypeReferenceGetTypeTest extends TestCase {
    public void testArrayFactory() {
        TypeReference<?> expectedListOfStringArray = new TypeReference<List<String>[]>() {};
        Type listOfString = new TypeReference<List<String>>() {}.getType();
//        assertEquals(expectedListOfStringArray, TypeReference.getArray(listOfString));
    }

    public void testParameterizedFactory() {
        TypeReference<?> expectedListOfListOfListOfString = new TypeReference<List<List<List<String>>>>() {};
//        Type listOfString = TypeReference.getParameterized(List.class, String.class).getType();
//        Type listOfListOfString = TypeReference.getParameterized(List.class, listOfString).getType();
//        assertEquals(expectedListOfListOfListOfString, TypeReference.getParameterized(List.class, listOfListOfString));
    }
}
