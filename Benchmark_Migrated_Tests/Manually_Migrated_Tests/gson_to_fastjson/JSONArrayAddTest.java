package com.alibaba.fastjson;

import com.google.gson.JsonNull;
import junit.framework.TestCase;
import org.junit.Assert;

public class JSONArrayAddTest extends TestCase {
    public void testEqualsNonEmptyArray() {
        JSONArray a = new JSONArray();
        JSONArray b = new JSONArray();

        assertEquals(a, a);

        a.add(new JSONObject());
        assertFalse(a.equals(b));
        assertFalse(b.equals(a));

        b.add(new JSONObject());
        assertEqualsAndHashCode(a, b);

        a.add(new JSONObject());
        assertFalse(a.equals(b));
        assertFalse(b.equals(a));

        b.add(JsonNull.INSTANCE);
        assertFalse(a.equals(b));
        assertFalse(b.equals(a));
    }

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

    public void testSet() {
        JSONArray array = new JSONArray();
        String a = "a";
        array.add(a);
        array.set(0, "b");
        assertEquals("b", array.getString(0));
        array.set(0, null);
        assertNull(array.get(0));
        array.set(0, "c");
        assertEquals("c", array.getString(0));
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

        assertEquals(1, original.getJSONArray(0).size());
        assertEquals(1, copy.getJSONArray(0).size());
    }

    public static void assertEqualsAndHashCode(Object a, Object b) {
        Assert.assertTrue(a.equals(b));
        Assert.assertTrue(b.equals(a));
        Assert.assertEquals(a.hashCode(), b.hashCode());
        Assert.assertFalse(a.equals(null));
        Assert.assertFalse(a.equals(new Object()));
    }
}
