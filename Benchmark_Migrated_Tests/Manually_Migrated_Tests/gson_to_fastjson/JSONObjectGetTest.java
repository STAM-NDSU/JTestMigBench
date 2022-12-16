package com.alibaba.fastjson;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.google.gson.JsonPrimitive;
import junit.framework.TestCase;


public class JSONObjectGetTest extends TestCase {
    public void testAddingAndRemovingObjectProperties() throws Exception {
        JSONObject jsonObj = new JSONObject();
        String propertyName = "property";
        assertFalse(jsonObj.containsKey(propertyName));
        assertNull(jsonObj.get(propertyName));

        String value = "blah";
        jsonObj.put(propertyName, value);
        assertEquals(value, jsonObj.get(propertyName));

        Object removedElement = jsonObj.remove(propertyName);
        assertEquals(value, removedElement);
        assertFalse(jsonObj.containsKey(propertyName));
        assertNull(jsonObj.get(propertyName));
    }

    public void testAddingNullPropertyValue() throws Exception {
        String propertyName = "property";
        JSONObject jsonObj = new JSONObject();
        jsonObj.put(propertyName, null);

        assertTrue(jsonObj.containsKey(propertyName));

        Object jsonElement = jsonObj.get(propertyName);
        assertNull(jsonElement);
    }

    public void testAddingBooleanProperties() throws Exception {
        String propertyName = "property";
        JSONObject jsonObj = new JSONObject();
        jsonObj.put(propertyName, true);

        assertTrue(jsonObj.containsKey(propertyName));

        Object jsonElement = jsonObj.get(propertyName);
        assertNotNull(jsonElement);
        assertTrue(jsonElement instanceof Boolean && (Boolean) jsonElement);
    }

    public void testAddingStringProperties() throws Exception {
        String propertyName = "property";
        String value = "blah";

        JSONObject jsonObj = new JSONObject();
        jsonObj.put(propertyName, value);

        assertTrue(jsonObj.containsKey(propertyName));

        Object jsonElement = jsonObj.get(propertyName);
        assertNotNull(jsonElement);
        assertEquals(value, jsonElement);
    }

    public void testAddingCharacterProperties() throws Exception {
        String propertyName = "property";
        char value = 'a';

        JSONObject jsonObj = new JSONObject();
        jsonObj.put(propertyName, value);

        assertTrue(jsonObj.containsKey(propertyName));

        Object jsonElement = jsonObj.get(propertyName);
        assertNotNull(jsonElement);
        assertEquals(String.valueOf(value), String.valueOf(jsonElement));
        assertEquals(value, (char) jsonElement);
    }

    public void testReadPropertyWithEmptyStringName() {
        DefaultJSONParser parser = new DefaultJSONParser("{\"\":true}");
        JSONObject jsonObj = parser.parseObject();
        assertEquals(true, (boolean) jsonObj.get(""));
    }

    public void testDeepCopy() {
        JSONObject original = new JSONObject();
        JSONArray firstEntry = new JSONArray();
        original.put("key", firstEntry);

        JSONObject copy = (JSONObject) original.clone();
        firstEntry.add(new JsonPrimitive("z"));

        assertEquals(1, ((JSONArray) original.get("key")).size());
        assertEquals(1, ((JSONArray) copy.get("key")).size());
    }
}
