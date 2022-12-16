package com.alibaba.fastjson;

import junit.framework.TestCase;

public class JSONObjectSizeTest extends TestCase {
    public void testSize() {
        JSONObject o = new JSONObject();
        assertEquals(0, o.size());

        o.put("Hello", 1);
        assertEquals(1, o.size());

        o.put("Hi", 1);
        assertEquals(2, o.size());

        o.remove("Hello");
        assertEquals(1, o.size());
    }

    public void testDeepCopy() {
        JSONObject original = new JSONObject();
        JSONArray firstEntry = new JSONArray();
        original.put("key", firstEntry);

        JSONObject copy = (JSONObject) original.clone();
        firstEntry.add("z");

        assertEquals(1, original.getJSONArray("key").size());
        assertEquals(1, copy.getJSONArray("key").size());
    }

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
