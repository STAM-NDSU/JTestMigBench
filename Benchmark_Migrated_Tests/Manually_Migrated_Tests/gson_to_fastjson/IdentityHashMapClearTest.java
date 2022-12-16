package com.alibaba.fastjson.util;

import junit.framework.TestCase;

public class IdentityHashMapClearTest extends TestCase {
    public void testClear() {
        IdentityHashMap<String, String> map = new IdentityHashMap<String, String>();
        map.put("a", "android");
        map.put("c", "cola");
        map.put("b", "bbq");
        map.clear();
        assertEquals(0, map.size());
    }

}
