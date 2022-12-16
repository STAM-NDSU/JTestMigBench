package com.alibaba.fastjson.util;

import junit.framework.TestCase;

import java.util.Random;

public class AntiCollisionHashMapGetTest extends TestCase {
    public void testPutOverrides() throws Exception {
        AntiCollisionHashMap<String, String> map = new AntiCollisionHashMap<String, String>();
        assertNull(map.put("d", "donut"));
        assertNull(map.put("e", "eclair"));
        assertNull(map.put("f", "froyo"));
        assertEquals(3, map.size());

        assertEquals("donut", map.get("d"));
        assertEquals("donut", map.put("d", "done"));
        assertEquals(3, map.size());
    }

    public void testEmptyStringValues() {
        AntiCollisionHashMap<String, String> map = new AntiCollisionHashMap<String, String>();
        map.put("a", "");
        assertTrue(map.containsKey("a"));
        assertEquals("", map.get("a"));
    }

    public void testForceDoublingAndRehash() throws Exception {
        Random random = new Random(1367593214724L);
        AntiCollisionHashMap<String, String> map = new AntiCollisionHashMap<String, String>();
        String[] keys = new String[1000];
        for (int i = 0; i < keys.length; i++) {
            keys[i] = Integer.toString(Math.abs(random.nextInt()), 36) + "-" + i;
            map.put(keys[i], "" + i);
        }

        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            assertTrue(map.containsKey(key));
            assertEquals("" + i, map.get(key));
        }
    }
}
