package org.json.junit;

import junit.framework.TestCase;
import org.json.JSONObject;


public class JSONObjectGetFloatTest extends TestCase {
    public void testParsingStringAsNumber() throws Exception {
        JSONObject json = new JSONObject("{'1':'1'}");

        assertFalse(json.get("1") instanceof Number);
        assertEquals(1F, json.getFloat("1"), 0.00001);
  }
}
