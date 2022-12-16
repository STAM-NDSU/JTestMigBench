package com.alibaba.fastjson;

import junit.framework.TestCase;


public class JSONArraySetTest extends TestCase {
    public void testSet() {
        JSONArray array = new JSONArray();
        array.set(0, 1);
        array.set(0, "b");
        assertEquals("b", array.getString(0));
        array.set(0, null);
        assertNull(array.get(0));
        array.set(0, "c");
        assertEquals("c", array.getString(0));
        assertEquals(1, array.size());
    }
}
