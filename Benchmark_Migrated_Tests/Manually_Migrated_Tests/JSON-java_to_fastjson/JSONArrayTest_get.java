package com.alibaba;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

public class JSONArrayTest_get {

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
        assertThrows(IndexOutOfBoundsException.class, () -> jsonArray.get(-1));
    }

    @Test
    public void testJSONArrayConstructor() {
        // should copy the array
        JSONArray a1 = JSONArray.parseArray("[2, \"test2\", true]");
        JSONArray a2 = new JSONArray(a1);
        assertNotNull("Should not error", a2);
        assertEquals("length", a1.size(), a2.size());

        for(int i = 0; i < a1.size(); i++) {
            assertEquals("index " + i + " are equal", a1.get(i), a2.get(i));
        }
    }

    /**
     * Verifies that the object constructor can properly handle any supported collection object.
     */
    @Test
    public void testJSONArrayPutAll() {
        // should copy the array
        JSONArray a1 = JSONArray.parseArray("[2, \"test2\", true]");
        JSONArray a2 = new JSONArray();
        a2.addAll(a1);
        assertNotNull("Should not error", a2);
        assertEquals("length", a1.size(), a2.size());

        for(int i = 0; i < a1.size(); i++) {
            assertEquals("index " + i + " are equal", a1.get(i), a2.get(i));
        }
    }

}
