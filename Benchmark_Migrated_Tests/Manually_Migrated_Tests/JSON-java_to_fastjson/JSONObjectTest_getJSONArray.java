package com.alibaba;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class JSONObjectTest_getJSONArray {

    @Test
    public void jsonObjectValues() {
        String str =
                "{"+
                        "\"trueKey\":true,"+
                        "\"falseKey\":false,"+
                        "\"trueStrKey\":\"true\","+
                        "\"falseStrKey\":\"false\","+
                        "\"stringKey\":\"hello world!\","+
                        "\"intKey\":42,"+
                        "\"intStrKey\":\"43\","+
                        "\"longKey\":1234567890123456789,"+
                        "\"longStrKey\":\"987654321098765432\","+
                        "\"doubleKey\":-23.45e7,"+
                        "\"doubleStrKey\":\"00001.000\","+
                        "\"BigDecimalStrKey\":\"19007199254740993.35481234487103587486413587843213584\","+
                        "\"negZeroKey\":-0.0,"+
                        "\"negZeroStrKey\":\"-0.0\","+
                        "\"arrayKey\":[0,1,2],"+
                        "\"objectKey\":{\"myKey\":\"myVal\"}"+
                        "}";
        JSONObject jsonObject = JSON.parseObject(str);

        JSONArray jsonArray = jsonObject.getJSONArray("arrayKey");
        assertTrue("arrayKey should be JSONArray",
                jsonArray.getIntValue(0) == 0 &&
                        jsonArray.getIntValue(1) == 1 &&
                        jsonArray.getIntValue(2) == 2);
        JSONObject jsonObjectInner = jsonObject.getJSONObject("objectKey");
        assertTrue("objectKey should be JSONObject",
                jsonObjectInner.get("myKey").equals("myVal"));
    }

    @Test
    public void jsonObjectNonAndWrongValues() {
        String str =
                "{"+
                        "\"trueKey\":true,"+
                        "\"falseKey\":false,"+
                        "\"trueStrKey\":\"true\","+
                        "\"falseStrKey\":\"false\","+
                        "\"stringKey\":\"hello world!\","+
                        "\"intKey\":42,"+
                        "\"intStrKey\":\"43\","+
                        "\"longKey\":1234567890123456789,"+
                        "\"longStrKey\":\"987654321098765432\","+
                        "\"doubleKey\":-23.45e7,"+
                        "\"doubleStrKey\":\"00001.000\","+
                        "\"arrayKey\":[0,1,2],"+
                        "\"objectKey\":{\"myKey\":\"myVal\"}"+
                        "}";
        JSONObject jsonObject = JSON.parseObject(str);
        assertNull(jsonObject.getJSONArray("nonKey"));
        assertThrows(JSONException.class, () -> jsonObject.getJSONArray("stringKey"));
    }

    @Test
    public void testIssue548ObjectWithEmptyJsonArray() {
        JSONObject jsonObject = JSON.parseObject("{\"empty_json_array\": []}");
        assertNotNull("'empty_json_array' should be an array", jsonObject.getJSONArray("empty_json_array"));
        assertEquals("'empty_json_array' should have a length of 0", 0, jsonObject.getJSONArray("empty_json_array").size());
    }

}
