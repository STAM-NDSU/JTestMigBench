package com.alibaba;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Iterator;

import static org.junit.Assert.assertTrue;

public class JSONArrayTest_iterator {

    @Test
    public void iteratorTest() {
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
        Iterator<Object> it = jsonArray.iterator();
        assertTrue("Array true",
                Boolean.TRUE.equals(it.next()));
        assertTrue("Array false",
                Boolean.FALSE.equals(it.next()));
        assertTrue("Array string true",
                "true".equals(it.next()));
        assertTrue("Array string false",
                "false".equals(it.next()));
        assertTrue("Array string",
                "hello".equals(it.next()));

        assertTrue("Array double [23.45e-4]",
                new BigDecimal("0.002345").equals(it.next()));
        assertTrue("Array string double",
                new Double(23.45).equals(Double.parseDouble((String)it.next())));

        assertTrue("Array value int",
                new Integer(42).equals(it.next()));
        assertTrue("Array value string int",
                new Integer(43).equals(Integer.parseInt((String)it.next())));

        JSONArray nestedJsonArray = (JSONArray)it.next();
        assertTrue("Array value JSONArray", nestedJsonArray != null);

        JSONObject nestedJsonObject = (JSONObject)it.next();
        assertTrue("Array value JSONObject", nestedJsonObject != null);

        assertTrue("Array value long",
                new Long(0).equals(((Number) it.next()).longValue()));
        assertTrue("Array value string long",
                new Long(-1).equals(Long.parseLong((String) it.next())));
        assertTrue("should be at end of array", !it.hasNext());
    }

}
