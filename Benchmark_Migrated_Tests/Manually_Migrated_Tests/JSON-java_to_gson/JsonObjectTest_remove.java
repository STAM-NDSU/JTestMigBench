package com.google.gson;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class JsonObjectTest_remove {

    @SuppressWarnings("boxing")
    @Test
    public void jsonObjectPut() {
        String expectedStr =
                "{"+
                        "\"trueKey\":true,"+
                        "\"falseKey\":false,"+
                        "\"arrayKey\":[0,1,2],"+
                        "\"objectKey\":{"+
                        "\"myKey1\":\"myVal1\","+
                        "\"myKey2\":\"myVal2\","+
                        "\"myKey3\":\"myVal3\","+
                        "\"myKey4\":\"myVal4\""+
                        "}"+
                        "}";
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("trueKey", true);
        jsonObject.addProperty("falseKey", false);

        jsonObject.add("arrayKey", new Gson().toJsonTree(new int[] {0, 1, 2}));

        Map<String, Object> myMap = new HashMap<String, Object>();
        myMap.put("myKey1", "myVal1");
        myMap.put("myKey2", "myVal2");
        myMap.put("myKey3", "myVal3");
        myMap.put("myKey4", "myVal4");

        jsonObject.add("objectKey", new Gson().toJsonTree(myMap));

        jsonObject.remove("trueKey");
        JsonObject expectedJsonObject = JsonParser.parseString(expectedStr).getAsJsonObject();

        assertNotEquals("unequal jsonObjects should not be similar", jsonObject, expectedJsonObject);
    }

    @Test
    public void jsonObjectputNull() {
        String str = "{\"myKey\": \"myval\"}";
        JsonObject jsonObjectRemove = JsonParser.parseString(str).getAsJsonObject();
        jsonObjectRemove.remove("myKey");
        assertEquals("jsonObject should be empty", 0, jsonObjectRemove.size());
    }

}