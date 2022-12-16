package com.alibaba;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class JSONObjectTest_remove {

    @Test
    public void jsonObjectputNull() {

        // put null should remove the item.
        String str = "{\"myKey\": \"myval\"}";
        JSONObject jsonObjectRemove = JSONObject.parseObject(str);
        jsonObjectRemove.remove("myKey");
        assertTrue("jsonObject should be empty", jsonObjectRemove.isEmpty());
    }

    @Test
    public void jsonObjectPut() {
        String expectedStr =
                "{"+
                        "\"trueKey\":true,"+
                        "\"falseKey\":false,"+
                        "\"arrayKey\":[0,1,2],"+
                        "\"objectKey\":{"+
                        "\"myKey1\":\"myVal1\","+
                        "\"myKey2\":\"myVal2\","+
                        "\"myKey3\":\"myVal3\","+
                        "\"myKey4\":\"myVal4\""+
                        "}"+
                        "}";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("trueKey", true);
        jsonObject.put("falseKey", false);
        Integer [] intArray = { 0, 1, 2 };
        jsonObject.put("arrayKey", Arrays.asList(intArray));
        Map<String, Object> myMap = new HashMap<String, Object>();
        myMap.put("myKey1", "myVal1");
        myMap.put("myKey2", "myVal2");
        myMap.put("myKey3", "myVal3");
        myMap.put("myKey4", "myVal4");
        jsonObject.put("objectKey", myMap);

        jsonObject.remove("trueKey");
        JSONObject expectedJsonObject = JSONObject.parseObject(expectedStr);
        assertNotEquals("unequal jsonObjects should not be similar", jsonObject, expectedJsonObject);
    }

}
