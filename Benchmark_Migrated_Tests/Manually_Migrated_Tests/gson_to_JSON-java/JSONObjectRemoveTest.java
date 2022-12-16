package org.json.junit;

import junit.framework.TestCase;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONObjectRemoveTest extends TestCase {
    public void testAddingAndRemovingObjectProperties() throws Exception {
        JSONObject jsonObj = new JSONObject();
        String propertyName = "property";
        assertFalse(jsonObj.has(propertyName));
        try {
            jsonObj.get(propertyName);
            fail();
        } catch (JSONException expected) { }


        String value = "blah";
        jsonObj.put(propertyName, value);
        assertEquals(value, jsonObj.get(propertyName));

        Object removedElement = jsonObj.remove(propertyName);
        assertEquals(value, removedElement);
        assertFalse(jsonObj.has(propertyName));
        try {
            jsonObj.get(propertyName);
            fail();
        } catch (JSONException expected) { }
    }

    public void testSize() {
        JSONObject o = new JSONObject();
        assertEquals(0, o.length());

        o.put("Hello", 1);
        assertEquals(1, o.length());

        o.put("Hi", 1);
        assertEquals(2, o.length());

        o.remove("Hello");
        assertEquals(1, o.length());
    }
}
