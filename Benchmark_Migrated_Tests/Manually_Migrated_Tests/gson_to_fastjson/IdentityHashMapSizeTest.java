package com.alibaba.fastjson.util;

import junit.framework.TestCase;

public class IdentityHashMapSizeTest extends TestCase {
    public void testPutOverrides() throws Exception {
        IdentityHashMap<String, String> map = new IdentityHashMap<String, String>();
        map.put("d", "donut");
        map.put("e", "eclair");
        map.put("f", "froyo");
        assertEquals(3, map.size());

        assertEquals("donut", map.get("d"));
        assertEquals(true, map.put("d", "done"));
        assertEquals(3, map.size());
    }

    public void testClear() {
        IdentityHashMap<String, String> map = new IdentityHashMap<String, String>();
        map.put("a", "android");
        map.put("c", "cola");
        map.put("b", "bbq");
        map.clear();
        assertEquals(0, map.size());
    }
}
