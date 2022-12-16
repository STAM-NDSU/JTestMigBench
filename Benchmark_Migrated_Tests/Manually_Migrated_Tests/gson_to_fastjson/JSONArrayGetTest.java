package com.alibaba.fastjson;

import junit.framework.TestCase;

public class JSONArrayGetTest extends TestCase {
    public void testSet() {
        JSONArray array = new JSONArray();
        String a = "a";
        array.add(a);
        array.set(0, "b");
        assertEquals("b", array.get(0));
        array.set(0, null);
        assertNull(array.get(0));
        array.set(0, "c");
        assertEquals("c", array.get(0));
        assertEquals(1, array.size());
    }

    public void testDeepCopy() {
        JSONArray original = new JSONArray();
        JSONArray firstEntry = new JSONArray();
        original.add(firstEntry);

        JSONArray copy = (JSONArray) original.clone();
        original.add("y");

        assertEquals(1, copy.size());
        firstEntry.add("z");

        assertEquals(1, ((JSONArray) original.get(0)).size());
        assertEquals(1, ((JSONArray) copy.get(0)).size());
    }
}
