package com.alibaba;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class JSONObjectTest_getString {

    @Test
    public void jsonObjectNonAndWrongValues() {
        String str =
                "{" +
                        "\"trueKey\":true," +
                        "\"falseKey\":false," +
                        "\"trueStrKey\":\"true\"," +
                        "\"falseStrKey\":\"false\"," +
                        "\"stringKey\":\"hello world!\"," +
                        "\"intKey\":42," +
                        "\"intStrKey\":\"43\"," +
                        "\"longKey\":1234567890123456789," +
                        "\"longStrKey\":\"987654321098765432\"," +
                        "\"doubleKey\":-23.45e7," +
                        "\"doubleStrKey\":\"00001.000\"," +
                        "\"arrayKey\":[0,1,2]," +
                        "\"objectKey\":{\"myKey\":\"myVal\"}" +
                        "}";
        JSONObject jsonObject = JSONObject.parseObject(str);
        assertNull(jsonObject.getString("nonKey"));
        assertEquals("true", jsonObject.getString("trueKey"));
    }

    @Test
    public void jsonObjectParseControlCharacters() {
        for (int i = 0; i <= 0x001f; i++) {
            final String charString = String.valueOf((char) i);
            final String source = "{\"key\":\"" + charString + "\"}";
            try {
                JSONObject jo = JSONObject.parseObject(source);
                assertTrue("Expected " + charString + "(" + i + ") in the JSON Object but did not find it.", charString.equals(jo.getString("key")));
            } catch (JSONException ex) {
                assertTrue("Only \\0 (U+0000), \\n (U+000A), and \\r (U+000D) should cause an error. Instead " + charString + "(" + i + ") caused an error",
                        i == '\0' || i == '\n' || i == '\r'
                );
            }
        }
    }

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
        JSONObject jsonObject = JSONObject.parseObject(str);
        assertTrue("stringKey should be string",
                jsonObject.getString("stringKey").equals("hello world!"));
    }



}
