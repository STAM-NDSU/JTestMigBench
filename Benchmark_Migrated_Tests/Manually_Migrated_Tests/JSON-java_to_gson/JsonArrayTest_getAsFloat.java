package com.google.gson;

import org.junit.Test;

import static org.junit.Assert.*;

public class JsonArrayTest_getAsFloat {

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
        assertEquals("Array double can be float", 23.45e-4f, jsonArray.get(5).getAsFloat(), 0.0);

    }

}
