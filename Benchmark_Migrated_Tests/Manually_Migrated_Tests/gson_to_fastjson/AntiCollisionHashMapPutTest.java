package com.alibaba.fastjson.util;

import junit.framework.TestCase;
import org.junit.Assert;

import java.util.*;

public class AntiCollisionHashMapPutTest extends TestCase {
    public void testIterationOrder() {
        AntiCollisionHashMap<String, String> map = new AntiCollisionHashMap<String, String>();
        map.put("a", "android");
        map.put("c", "cola");
        map.put("b", "bbq");
        assertIterationOrder(map.keySet(), "a", "c", "b");
        assertIterationOrder(map.values(), "android", "cola", "bbq");
    }

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

    public void testPutNullKeyFails() {
        AntiCollisionHashMap<String, String> map = new AntiCollisionHashMap<String, String>();
        map.put(null, "android");
        assertEquals("android", map.get(null));
    }

    public void testPutNonComparableKeyFails() {
        AntiCollisionHashMap<Object, String> map = new AntiCollisionHashMap<Object, String>();
        Object obj = new Object();
        map.put(obj, "android");
        assertEquals("android", map.get(obj));
    }

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

    public void testClear() {
        AntiCollisionHashMap<String, String> map = new AntiCollisionHashMap<String, String>();
        map.put("a", "android");
        map.put("c", "cola");
        map.put("b", "bbq");
        map.clear();
        assertIterationOrder(map.keySet());
        assertEquals(0, map.size());
    }

    public void testEqualsAndHashCode() throws Exception {
        AntiCollisionHashMap<String, Integer> map1 = new AntiCollisionHashMap<String, Integer>();
        map1.put("A", 1);
        map1.put("B", 2);
        map1.put("C", 3);
        map1.put("D", 4);

        AntiCollisionHashMap<String, Integer> map2 = new AntiCollisionHashMap<String, Integer>();
        map2.put("C", 3);
        map2.put("B", 2);
        map2.put("D", 4);
        map2.put("A", 1);

        assertEqualsAndHashCode(map1, map2);
    }

    private <T> void assertIterationOrder(Iterable<T> actual, T... expected) {
        ArrayList<T> actualList = new ArrayList<T>();
        for (T t : actual) {
            actualList.add(t);
        }
        assertEquals(Arrays.asList(expected), actualList);
    }

    public static void assertEqualsAndHashCode(Object a, Object b) {
        Assert.assertTrue(a.equals(b));
        Assert.assertTrue(b.equals(a));
        Assert.assertEquals(a.hashCode(), b.hashCode());
        Assert.assertFalse(a.equals(null));
        Assert.assertFalse(a.equals(new Object()));
    }
}
