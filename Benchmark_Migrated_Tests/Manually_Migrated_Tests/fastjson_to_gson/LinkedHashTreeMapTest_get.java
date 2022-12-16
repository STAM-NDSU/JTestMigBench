package com.google.gson.migration;

import com.google.gson.internal.LinkedHashTreeMap;
import org.junit.Test;
import static org.junit.Assert.*;

public class LinkedHashTreeMapTest_get {

    @Test
    public void test_0() throws Exception {
        final LinkedHashTreeMap<Object, Object> m = new LinkedHashTreeMap<>();

        for (int i = 0; i < 100; ++i) {
            m.put(i, i);
        }

        for (int i = 0; i < 100; ++i) {
            assertEquals(i, m.get(i));
            assertTrue(m.containsKey(i));
        }
    }

}
