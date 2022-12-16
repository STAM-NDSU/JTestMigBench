package com.alibaba;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class JSONArrayTest_getInt {

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
        assertThrows(NumberFormatException.class, () -> jsonArray.getIntValue(4));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void jsonArrayClearMethodTest() {
        //Adds random stuff to the JSONArray
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(123);
        jsonArray.add("456");
        jsonArray.add(new JSONArray());
        jsonArray.clear(); //Clears the JSONArray
        assertTrue("expected jsonArray.length() == 0", jsonArray.size() == 0); //Check if its length is 0
        jsonArray.getIntValue(0); //Should throws org.json.JSONException: JSONArray[0] not found
    }

    @Test
    public void verifyPutAll() {
        final JSONArray jsonArray = new JSONArray();

        // array
        List<Integer> myInts = Arrays.asList(1, 2, 3, 4, 5);
        jsonArray.addAll(myInts);

        assertEquals("int arrays lengths should be equal",
                jsonArray.size(),
                myInts.size());

        for (int i = 0; i < myInts.size(); i++) {
            assertEquals("int arrays elements should be equal",
                    myInts.get(i).intValue(),
                    jsonArray.getIntValue(i));
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
        // ints
        assertTrue("Array value int",
                new Integer(42).equals(jsonArray.getIntValue(7)));
        assertTrue("Array value string int",
                new Integer(43).equals(jsonArray.getIntValue(8)));
    }

}
