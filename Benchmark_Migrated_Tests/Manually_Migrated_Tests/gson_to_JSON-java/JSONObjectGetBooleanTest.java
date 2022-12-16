package org.json.junit;

import junit.framework.TestCase;
import org.json.JSONObject;


public class JSONObjectGetBooleanTest extends TestCase {
    public void testBoolean() throws Exception {
        JSONObject json = new JSONObject("{'Boolean.TRUE':true}");

        assertTrue(json.get("Boolean.TRUE") instanceof Boolean);
        assertTrue(json.getBoolean("Boolean.TRUE"));

        // Extra support for booleans
        json = new JSONObject("{1:false}");
        assertFalse(json.getBoolean("1"));

        json = new JSONObject("{1:false}");
        assertFalse(json.getBoolean("1"));

        json = new JSONObject("{'true':true}");
        assertTrue(json.getBoolean("true"));

        json = new JSONObject("{'TrUe':true}");
        assertTrue(json.getBoolean("TrUe"));

        json = new JSONObject("{'1.3':false}");
        assertFalse(json.getBoolean("1.3"));
    }

    public void testParsingStringAsBoolean() throws Exception {
        JSONObject json = new JSONObject("{'true':'true'}");

        assertFalse(json.get("true") instanceof Boolean);
        assertTrue(json.getBoolean("true"));
    }

}
