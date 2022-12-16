package org.json.junit;

import junit.framework.TestCase;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONObjectGetStringTest extends TestCase {
    public void testStringsAndChar() throws Exception {
        JSONObject json = new JSONObject("{'abc':'abc'}");
        assertTrue(json.get("abc") instanceof String);
        assertEquals("abc", json.getString("abc"));

        json = new JSONObject("{'z':'z'}");
        assertTrue(json.get("z") instanceof String);
        assertEquals("z", json.getString("z"));

        json = new JSONObject("{'true':true}");
        try {
            json.getString("true");
            fail();
        } catch (JSONException exception) {  }

    }
}
