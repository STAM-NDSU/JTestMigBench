package com.alibaba.fastjson;

import junit.framework.TestCase;

public class JSONObjectKeySetTest extends TestCase {
    public void testKeySet() {
        JSONObject a = new JSONObject();

        a.put("foo", new JSONArray());
        a.put("bar", new JSONObject());

        assertEquals(2, a.size());
        assertEquals(2, a.keySet().size());
        assertTrue(a.keySet().contains("foo"));
        assertTrue(a.keySet().contains("bar"));
    }
}
