package com.google.gson;

import org.junit.Test;

import static org.junit.Assert.*;

public class JsonArrayTest_getAsBoolean {

    @Test
    public void getArrayValues() {
        String arrayStr =
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

        JsonArray jsonArray = JsonParser.parseString(arrayStr).getAsJsonArray();
        // booleans
        assertTrue("Array true",
                jsonArray.get(0).getAsBoolean());
        assertTrue("Array false",
                !jsonArray.get(1).getAsBoolean());
        assertTrue("Array string true",
                jsonArray.get(2).getAsBoolean());
        assertTrue("Array string false",
                !jsonArray.get(3).getAsBoolean());
    }

    @Test
    public void failedGetArrayValues() {
        String arrayStr =
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

        JsonArray jsonArray = JsonParser.parseString(arrayStr).getAsJsonArray();
        assertFalse(jsonArray.get(4).getAsBoolean());
    }

}
