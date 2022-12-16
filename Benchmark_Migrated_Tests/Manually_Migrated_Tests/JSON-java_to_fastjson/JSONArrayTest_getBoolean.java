package com.alibaba;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import org.junit.Test;

import static org.junit.Assert.*;

public class JSONArrayTest_getBoolean {

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

        com.alibaba.fastjson.JSONArray jsonArray = com.alibaba.fastjson.JSONArray.parseArray(arrayStr);
        // booleans
        assertEquals("Array true", true, (boolean) jsonArray.getBoolean(0));
        assertEquals("Array false", false, (boolean) jsonArray.getBoolean(1));
        assertEquals("Array string true", true, (boolean) jsonArray.getBoolean(2));
        assertEquals("Array string false", false, (boolean) jsonArray.getBoolean(3));
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

        com.alibaba.fastjson.JSONArray jsonArray = JSONArray.parseArray(arrayStr);
        assertThrows(JSONException.class, () -> jsonArray.getBoolean(4));
    }


}
