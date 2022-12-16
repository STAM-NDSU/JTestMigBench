package com.google.gson.migration;

import com.google.gson.internal.LinkedHashTreeMap;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class LinkedHashTreeMapTest_contains {

    @Test
    public void test_0() throws Exception {
        final LinkedHashTreeMap<Object, Object> m = new LinkedHashTreeMap<>();

        for (int i = 0; i < 100; ++i) {
            m.put(i, i);
        }

        assertFalse(m.entrySet().contains(1));
        assertTrue(m.entrySet().contains(m.entrySet().iterator().next()));
    }
}
