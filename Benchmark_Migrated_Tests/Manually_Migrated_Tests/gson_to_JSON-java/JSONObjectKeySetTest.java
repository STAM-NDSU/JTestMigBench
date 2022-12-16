package org.json.junit;

import junit.framework.TestCase;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONObjectKeySetTest extends TestCase {
    public void testKeySet() {
        JSONObject a = new JSONObject();

        a.put("foo", new JSONArray());
        a.put("bar", new JSONObject());

        assertEquals(2, a.length());
        assertEquals(2, a.keySet().size());
        assertTrue(a.keySet().contains("foo"));
        assertTrue(a.keySet().contains("bar"));
    }
}
