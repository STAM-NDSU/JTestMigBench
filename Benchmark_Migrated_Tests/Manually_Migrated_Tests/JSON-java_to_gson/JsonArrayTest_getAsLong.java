package com.google.gson;

import org.junit.Test;

import static org.junit.Assert.*;

public class JsonArrayTest_getAsLong {

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

        final JsonArray jsonArray = JsonParser.parseString(arrayStr).getAsJsonArray();
        // longs
        assertEquals("Array value long", 0, jsonArray.get(11).getAsLong());
        assertEquals("Array value string long", (long) -1, jsonArray.get(12).getAsLong());
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

        final JsonArray jsonArray = JsonParser.parseString(arrayStr).getAsJsonArray();
        assertThrows(NumberFormatException.class, () -> jsonArray.get(4).getAsLong());
    }

}
