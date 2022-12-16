package org.json.junit;

import junit.framework.TestCase;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONObjectGetDoubleTest extends TestCase {
    public void testExponential() throws Exception {
        JSONObject json = new JSONObject("{'1E+7':'1E+7'}");

        assertEquals(1E+7, json.getDouble("1E+7"), 0.00001);
        assertEquals(1E+7, json.getDouble("1E+7"), 0.00001);

        try {
            json.getInt("1E+7");
            fail("JSONObject[\"1E+7\"] is not a int.");
        } catch (JSONException expected) { }
    }

    public void testParsingStringAsNumber() throws Exception {
        JSONObject json = new JSONObject("{'1':'1'}");

        assertFalse(json.get("1") instanceof Number );
        assertEquals(1D, json.getDouble("1"), 0.00001);
    }
}
