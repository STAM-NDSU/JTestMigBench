package com.google.gson;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JsonArrayTest_getAsString {

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
        // strings
        assertEquals("Array value string", "hello", jsonArray.get(4).getAsString());
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
        assertEquals("23.45e-4", jsonArray.get(5).getAsString());
    }

    @Test
    public void verifyPutAll() {
        final JsonArray jsonArray = new JsonArray();

        // collection
        List<String> myList = Arrays.asList("one", "two", "three", "four", "five");
        for (String s : myList) {
            jsonArray.add(s);
        }
        int len = myList.size();

        assertEquals("arrays lengths should be equal",
                jsonArray.size(),
                len);

        for (int i = 0; i < myList.size(); i++) {
            assertEquals("collection elements should be equal",
                    myList.get(i),
                    jsonArray.get(i).getAsString());
        }
    }

}
