package com.alibaba.fastjson.util;

import junit.framework.TestCase;

import java.util.Random;

public class AntiCollisionHashMapContainsKeyTest extends TestCase {
    public void testContainsNonComparableKeyReturnsFalse() {
        AntiCollisionHashMap<String, String> map = new AntiCollisionHashMap<String, String>();
        map.put("a", "android");
        assertFalse(map.containsKey(new Object()));
    }

    public void testContainsNullKeyIsAlwaysFalse() {
        AntiCollisionHashMap<String, String> map = new AntiCollisionHashMap<String, String>();
        map.put("a", "android");
        assertFalse(map.containsKey(null));
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
