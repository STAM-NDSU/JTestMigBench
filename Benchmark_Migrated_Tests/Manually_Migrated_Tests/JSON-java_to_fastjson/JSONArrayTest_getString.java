package com.alibaba;

import com.alibaba.fastjson.JSONArray;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JSONArrayTest_getString {

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
        jsonArray.getString(5);
    }

    @Test
    public void verifyPutAll() {
        final JSONArray jsonArray = new JSONArray();

        // array
        final List<Integer> myInts = Arrays.asList(1, 2, 3, 4, 5);
        jsonArray.addAll(myInts);

        assertEquals("int arrays lengths should be equal",
                jsonArray.size(),
                myInts.size());

        // collection
        List<String> myList = Arrays.asList("one", "two", "three", "four", "five");
        jsonArray.addAll(myList);

        int len = myInts.size() + myList.size();

        assertEquals("arrays lengths should be equal",
                jsonArray.size(),
                len);

        for (int i = 0; i < myList.size(); i++) {
            assertEquals("collection elements should be equal",
                    myList.get(i),
                    jsonArray.getString(myInts.size() + i));
        }
    }

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
        assertTrue("Array value string",
                "hello".equals(jsonArray.getString(4)));
    }

}
