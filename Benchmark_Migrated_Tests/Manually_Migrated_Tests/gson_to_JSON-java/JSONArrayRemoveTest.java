package org.json.junit;

import junit.framework.TestCase;
import org.json.JSONArray;

public class JSONArrayRemoveTest extends TestCase {
    public void testRemove() {
        JSONArray array = new JSONArray();
        assertNull(array.remove(0));

        String a = "a";
        array.put(a);
        array.put("b");

        assertEquals("b", array.remove(1).toString());
        assertEquals(1, array.length());
        assertTrue(array.toList().contains(a));
    }
}
