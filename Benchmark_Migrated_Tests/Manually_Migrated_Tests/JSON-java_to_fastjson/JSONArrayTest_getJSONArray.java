package com.alibaba;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import org.junit.Test;


import static org.junit.Assert.*;

public class JSONArrayTest_getJSONArray {

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
        JSONArray nestedJsonArray = jsonArray.getJSONArray(9);
        assertTrue("Array value JSONArray", nestedJsonArray != null);
    }

    /**
     * Create a JSONArray doc with a variety of different elements.
     * Confirm that attempting to get the wrong types via the get[type]()
     * API methods result in JSONExceptions
     */
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
        assertThrows(ClassCastException.class, () -> jsonArray.getJSONArray(4));
    }

    /**
     * Confirm the JSONArray.length() method
     */
    @Test
    public void length() {
        assertTrue("expected empty JSONArray length 0",
                new JSONArray().size() == 0);
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
        assertTrue("expected JSONArray length 13. instead found "+jsonArray.size(), jsonArray.size() == 13);
        JSONArray nestedJsonArray = jsonArray.getJSONArray(9);
        assertTrue("expected JSONArray size 1", nestedJsonArray.size() == 1);
    }


}
