package com.google.gson;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class JsonObjectTest_getAsJsonArray {

    @Test
    public void toMap() {
        String jsonObjectStr =
                "{" +
                        "\"key1\":" +
                        "[1,2," +
                        "{\"key3\":true}" +
                        "]," +
                        "\"key2\":" +
                        "{\"key1\":\"val1\",\"key2\":" +
                        "{\"key2\":null}," +
                        "\"key3\":42" +
                        "}," +
                        "\"key3\":" +
                        "[" +
                        "[\"value1\",2.1]" +
                        "," +
                        "[null]" +
                        "]" +
                        "}";

        JsonObject jsonObject = JsonParser.parseString(jsonObjectStr).getAsJsonObject();
        Map<?,?> map = new Gson().fromJson(jsonObject, Map.class);

        List<?> key3List = (List<?>)map.get("key3");

        List<?> key3Val1List = (List<?>)key3List.get(0);

        // Assert that toMap() is a deep copy
        jsonObject.get("key3").getAsJsonArray().get(0).getAsJsonArray().set(0, new JsonPrimitive("still value 1"));
        assertEquals("key3 list val 1 list element 1 should be value1", "value1", key3Val1List.get(0));
    }

    @Test
    public void testIssue548ObjectWithEmptyJsonArray() {
        JsonObject jsonObject = JsonParser.parseString("{\"empty_json_array\": []}").getAsJsonObject();
        assertTrue("missing expected key 'empty_json_array'", jsonObject.has("empty_json_array"));
        assertTrue("'empty_json_array' should be an array", jsonObject.get("empty_json_array").isJsonArray());
        assertEquals("'empty_json_array' should have a length of 0", 0, jsonObject.get("empty_json_array").getAsJsonArray().size());
    }

    @Test
    public void jsonObjectValues() {
        String str =
                "{"+
                        "\"trueKey\":true,"+
                        "\"falseKey\":false,"+
                        "\"trueStrKey\":\"true\","+
                        "\"falseStrKey\":\"false\","+
                        "\"stringKey\":\"hello world!\","+
                        "\"intKey\":42,"+
                        "\"intStrKey\":\"43\","+
                        "\"longKey\":1234567890123456789,"+
                        "\"longStrKey\":\"987654321098765432\","+
                        "\"doubleKey\":-23.45e7,"+
                        "\"doubleStrKey\":\"00001.000\","+
                        "\"BigDecimalStrKey\":\"19007199254740993.35481234487103587486413587843213584\","+
                        "\"negZeroKey\":-0.0,"+
                        "\"negZeroStrKey\":\"-0.0\","+
                        "\"arrayKey\":[0,1,2],"+
                        "\"objectKey\":{\"myKey\":\"myVal\"}"+
                        "}";
        JsonObject jsonObject = JsonParser.parseString(str).getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("arrayKey");
        assertTrue("arrayKey should be JSONArray",
                jsonArray.get(0).getAsInt() == 0 &&
                        jsonArray.get(1).getAsInt() == 1 &&
                        jsonArray.get(2).getAsInt() == 2);
    }

    @Test
    public void jsonObjectNonAndWrongValues() {
        String str =
                "{"+
                        "\"trueKey\":true,"+
                        "\"falseKey\":false,"+
                        "\"trueStrKey\":\"true\","+
                        "\"falseStrKey\":\"false\","+
                        "\"stringKey\":\"hello world!\","+
                        "\"intKey\":42,"+
                        "\"intStrKey\":\"43\","+
                        "\"longKey\":1234567890123456789,"+
                        "\"longStrKey\":\"987654321098765432\","+
                        "\"doubleKey\":-23.45e7,"+
                        "\"doubleStrKey\":\"00001.000\","+
                        "\"arrayKey\":[0,1,2],"+
                        "\"objectKey\":{\"myKey\":\"myVal\"}"+
                        "}";
        JsonObject jsonObject = JsonParser.parseString(str).getAsJsonObject();

        assertNull("Should be null", jsonObject.getAsJsonArray("nonKey"));

        assertThrows("stringKey value is a string, not an array, should throw a casting exception", ClassCastException.class,
                () -> jsonObject.getAsJsonArray("stringKey"));
    }

}