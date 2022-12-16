package com.google.gson;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class JsonArrayTest_get {

    @Test
    public void testJSONArrayConstructor() {
        // should copy the array
        JsonArray a1 = JsonParser.parseString("[2, \"test2\", true]").getAsJsonArray();
        JsonArray a2 = new JsonArray(); a2.addAll(a1);
        assertNotNull("Should not error", a2);
        assertEquals("length", a1.size(), a2.size());

        for(int i = 0; i < a1.size(); i++) {
            assertEquals("index " + i + " are equal", a1.get(i), a2.get(i));
        }
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

        assertThrows(IndexOutOfBoundsException.class, () -> jsonArray.get(-1));
    }

}
