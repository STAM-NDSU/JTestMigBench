package org.json.junit;

import junit.framework.TestCase;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class JSONObjectGetBigDecimalTest extends TestCase {
    public void testExponential() throws Exception {
        JSONObject json = new JSONObject("{'1E+7':'1E+7'}");

        assertEquals(new BigDecimal("1E+7"), json.getBigDecimal("1E+7"));

        try {
            json.getInt("1E+7");
            fail("JSONObject[\"1E+7\"] is not a int.");
        } catch (JSONException expected) { }
    }

    public void testParsingStringAsNumber() {
        JSONObject json = new JSONObject("{'1':'1'}");

        assertFalse(json.get("1") instanceof Number );
        assertEquals(new BigDecimal("1"), json.getBigDecimal("1"));
    }

}
