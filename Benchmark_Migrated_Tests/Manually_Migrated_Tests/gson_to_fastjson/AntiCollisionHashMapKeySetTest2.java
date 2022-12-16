package com.alibaba.fastjson.util;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public class AntiCollisionHashMapKeySetTest2 extends TestCase {
    public void testIterationOrder() {
        AntiCollisionHashMap<String, String> map = new AntiCollisionHashMap<String, String>();
        map.put("a", "android");
        map.put("c", "cola");
        map.put("b", "bbq");
        assertTrue(map.keySet().contains("a"));
        assertTrue(map.keySet().contains("c"));
        assertTrue(map.keySet().contains("b"));
    }

    public void testRemoveRootDoesNotDoubleUnlink() {
        AntiCollisionHashMap<String, String> map = new AntiCollisionHashMap<String, String>();
        map.put("a", "android");
        map.put("c", "cola");
        map.put("b", "bbq");
        Iterator<Map.Entry<String,String>> it = map.entrySet().iterator();
        it.next();
        it.next();
        it.next();
        it.remove();
        assertIterationOrder(map.keySet(), "a", "c");
    }

    public void testClear() {
        AntiCollisionHashMap<String, String> map = new AntiCollisionHashMap<String, String>();
        map.put("a", "android");
        map.put("c", "cola");
        map.put("b", "bbq");
        map.clear();
        assertIterationOrder(map.keySet());
        assertEquals(0, map.size());
    }

    private <T> void assertIterationOrder(Iterable<T> actual, T... expected) {
        ArrayList<T> actualList = new ArrayList<T>();
        for (T t : actual) {
            actualList.add(t);
        }
        assertEquals(Arrays.asList(expected), actualList);
    }
}
