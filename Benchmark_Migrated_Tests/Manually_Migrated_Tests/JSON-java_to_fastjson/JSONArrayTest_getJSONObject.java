package com.alibaba;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class JSONArrayTest_getJSONObject {

    @Test
    public void getArrayValues() {
        final String arrayStr =
                "["+
                        "true,"+
                        "false,"+
                        "\"true\","+
                        "\"false\","+
                        "\"hello\","+
                        "23.45e-4,"+
                        "\"23.45\","+
                        "42,"+
                        "\"43\","+
                        "["+
                        "\"world\""+
                        "],"+
                        "{"+
                        "\"key1\":\"value1\","+
                        "\"key2\":\"value2\","+
                        "\"key3\":\"value3\","+
                        "\"key4\":\"value4\""+
                        "},"+
                        "0,"+
                        "\"-1\""+
                        "]";

        JSONArray jsonArray = JSONArray.parseArray(arrayStr);
        // nested objects
        JSONArray nestedJsonArray = jsonArray.getJSONArray(9);
        assertTrue("Array value JSONArray", nestedJsonArray != null);
        JSONObject nestedJsonObject = jsonArray.getJSONObject(10);
        assertTrue("Array value JSONObject", nestedJsonObject != null);
    }

    @Test
    public void failedGetArrayValues() {
        final String arrayStr =
                "["+
                        "true,"+
                        "false,"+
                        "\"true\","+
                        "\"false\","+
                        "\"hello\","+
                        "23.45e-4,"+
                        "\"23.45\","+
                        "42,"+
                        "\"43\","+
                        "["+
                        "\"world\""+
                        "],"+
                        "{"+
                        "\"key1\":\"value1\","+
                        "\"key2\":\"value2\","+
                        "\"key3\":\"value3\","+
                        "\"key4\":\"value4\""+
                        "},"+
                        "0,"+
                        "\"-1\""+
                        "]";

        JSONArray jsonArray = JSONArray.parseArray(arrayStr);
        assertThrows(ClassCastException.class, () -> jsonArray.getJSONObject(4));
    }

    @Test
    public void toList() {
        String jsonArrayStr =
                "[" +
                        "[1,2," +
                        "{\"key3\":true}" +
                        "]," +
                        "{\"key1\":\"val1\",\"key2\":" +
                        "{\"key2\":null}," +
                        "\"key3\":42,\"key4\":[]" +
                        "}," +
                        "[" +
                        "[\"value1\",2.1]" +
                        "," +
                        "[null]" +
                        "]" +
                        "]";

        JSONArray jsonArray = JSONArray.parseArray(jsonArrayStr);
        List<?> list = jsonArray.toJavaList(Object.class);

        Map<?,?> val2Map = (Map<?,?>) list.get(1);

        // assert that toList() is not a deep copy
        jsonArray.getJSONObject(1).put("key1", "still val1");
        assertTrue("val2 map key 1 should be val1", val2Map.get("key1").equals("still val1"));
    }

}
