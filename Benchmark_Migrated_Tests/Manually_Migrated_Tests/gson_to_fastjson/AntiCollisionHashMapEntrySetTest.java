package com.alibaba.fastjson.util;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public class AntiCollisionHashMapEntrySetTest extends TestCase {
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

    private <T> void assertIterationOrder(Iterable<T> actual, T... expected) {
        ArrayList<T> actualList = new ArrayList<T>();
        for (T t : actual) {
            actualList.add(t);
        }
        assertEquals(Arrays.asList(expected), actualList);
    }
}
