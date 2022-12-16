package org.json.junit;

import junit.framework.TestCase;
import org.json.JSONObject;

public class JSONObjectGetLongTest extends TestCase {
    public void testParsingStringAsNumber() throws Exception {
        JSONObject json = new JSONObject("{'1':'1'}");

        assertFalse(json.get("1") instanceof Number);
        assertEquals(1L, json.getLong("1"));
    }
}
