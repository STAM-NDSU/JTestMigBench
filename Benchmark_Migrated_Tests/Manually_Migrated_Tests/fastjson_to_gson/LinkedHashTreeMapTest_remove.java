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

public class LinkedHashTreeMapTest_remove {

    @Test
    public void test_0() throws Exception {
        final LinkedHashTreeMap<Integer, Integer> m = new LinkedHashTreeMap<>();

        for (int i = 0; i < 100; ++i) {
            m.put(i, i);
        }

        final LinkedHashTreeMap<Integer, Integer> m2 = new LinkedHashTreeMap<>();
        m2.putAll(m);
        assertEquals(m.size(), m2.size());

        for (int i = 0; i < 100; ++i) {
            m2.remove(i, i);
        }

        m2.put(1, 1);
        assertFalse(m2.containsKey(null));
        assertTrue(m2.containsKey(1));
        assertFalse(m2.containsValue(null));
        assertTrue(m2.containsValue(1));
        Iterator iterator = m2.entrySet().iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
        m2.clear();

        assertFalse(m.entrySet().contains(1));
        assertTrue(m.entrySet().contains(m.entrySet().iterator().next()));
        m.entrySet().size();
        m.entrySet().remove(1);
        m.entrySet().remove(m.entrySet().iterator().next());
        m.entrySet().clear();

        {
            ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
            ObjectOutputStream objOut = new ObjectOutputStream(bytesOut);
            objOut.writeObject(m);
            objOut.flush();

            byte[] bytes = bytesOut.toByteArray();

            ByteArrayInputStream bytesIn = new ByteArrayInputStream(bytes);
            ObjectInputStream objIn = new ObjectInputStream(bytesIn);

            Object obj = objIn.readObject();

            assertEquals(LinkedHashMap.class, obj.getClass());
            assertEquals(m, obj);
        }
    }

}
