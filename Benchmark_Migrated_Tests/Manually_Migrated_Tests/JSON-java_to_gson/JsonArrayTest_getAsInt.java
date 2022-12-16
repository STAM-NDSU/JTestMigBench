package com.google.gson;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class JsonArrayTest_getAsInt {

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
        assertThrows(NumberFormatException.class, () -> jsonArray.get(4).getAsInt());
    }

    @Test
    public void jsonArrayClearMethodTest() {
        //Adds random stuff to the JSONArray
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(123);
        jsonArray.add("456");
        jsonArray.add(new JsonArray());
        for (int i = 2; i >= 0; i--) {
            jsonArray.remove(i);
        }
        assertTrue("Should be empty", jsonArray.isEmpty());
        assertThrows(IndexOutOfBoundsException.class, () -> jsonArray.get(0).getAsInt());
    }

    @Test
    public void verifyPutAll() {
        final JsonArray jsonArray = new JsonArray();

        // array
        int[] myInts = { 1, 2, 3, 4, 5 };
        for (int myInt : myInts) {
            jsonArray.add(myInt);
        }

        assertEquals("int arrays lengths should be equal",
                jsonArray.size(),
                myInts.length);

        for (int i = 0; i < myInts.length; i++) {
            assertEquals("int arrays elements should be equal",
                    myInts[i],
                    jsonArray.get(i).getAsInt());
        }
    }

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
        // ints
        assertEquals("Array value int", 42, jsonArray.get(7).getAsInt());
        assertEquals("Array value string int", 43, jsonArray.get(8).getAsInt());
    }

}
