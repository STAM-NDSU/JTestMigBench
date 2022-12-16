package com.alibaba.fastjson;

import junit.framework.TestCase;

public class JSONArrayRemoveTest extends TestCase {
    public void testRemove() {
        JSONArray array = new JSONArray();
        try {
            array.remove(0);
            fail();
        } catch (IndexOutOfBoundsException expected) {}
        String a = "a";
        array.add(a);
        assertTrue(array.remove(a));
        assertFalse(array.contains(a));
        array.add(a);
        array.add("b");
        assertEquals("b", array.remove(1));
        assertEquals(1, array.size());
        assertTrue(array.contains(a));
    }
}
