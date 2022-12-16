package com.google.gson.migration;

import com.google.gson.internal.LinkedHashTreeMap;
import org.junit.Test;

import static org.junit.Assert.*;

public class LinkedHashTreeMapTest_put {

    @Test
    public void test_0() throws Exception {
        LinkedHashTreeMap<Integer, Integer> m = new LinkedHashTreeMap<>();

        for (int i = 0; i < 100; ++i) {
            m.put(i, i);
        }

        LinkedHashTreeMap<Integer, Integer> m2 = new LinkedHashTreeMap<>();
        m2.putAll(m);
        assertEquals(m.size(), m2.size());

        LinkedHashTreeMap<Integer, Object> m6 = new LinkedHashTreeMap<>();
        m6.putAll(m);
        assertEquals(m.size(), m6.size());
        m6.put(100000, "a");
        m6.put(100001, "b");

        for (int i = 0; i < 100; ++i) {
            assertEquals(i, m.get(i).intValue());
            assertTrue(m.containsKey(i));
        }

        for (int i = 0; i < 100; ++i) {
            m2.remove(i, i);
        }
        assertThrows(NullPointerException.class, () -> m2.put(null, null));
        m2.put(1, 1);
        assertFalse(m2.containsKey(null));
        assertTrue(m2.containsKey(1));
        assertFalse(m2.containsValue(null));
        assertTrue(m2.containsValue(1));
    }

}
