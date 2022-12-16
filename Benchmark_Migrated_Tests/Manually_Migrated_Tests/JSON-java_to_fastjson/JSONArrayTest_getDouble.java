package com.alibaba;

import com.alibaba.fastjson.JSONArray;
import org.junit.Test;

import static org.junit.Assert.*;

public class JSONArrayTest_getDouble {

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
        assertThrows(NumberFormatException.class, () -> jsonArray.getDouble(4));
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
        assertTrue("Array double",
                new Double(23.45e-4).equals(jsonArray.getDouble(5)));
        assertTrue("Array string double",
                new Double(23.45).equals(jsonArray.getDouble(6)));
    }

}
