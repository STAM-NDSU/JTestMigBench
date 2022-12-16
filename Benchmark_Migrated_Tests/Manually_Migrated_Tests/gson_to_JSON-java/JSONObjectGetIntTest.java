package org.json.junit;

import junit.framework.TestCase;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONObjectGetIntTest extends TestCase {
    public void testExponential() throws Exception {
        JSONObject json = new JSONObject("{'1E+7':'1E+7'}");

        try {
            json.getInt("1E+7");
            fail("JSONObject[\"1E+7\"] is not a int.");
        } catch (JSONException expected) { }
    }

    public void testParsingStringAsNumber() throws Exception {
        JSONObject json = new JSONObject("{'1':'1'}");

        assertFalse(json.get("1") instanceof Number);
        assertEquals(1, json.getInt("1"));
    }
}
