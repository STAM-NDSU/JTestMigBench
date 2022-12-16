package com.google.gson;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class JsonPrimitiveTest_getAsString {

    /**
     * Tests how JSONObject get[type] handles incorrect types
     */
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
        JsonObject jsonObject = JsonParser.parseString(str).getAsJsonObject();

        assertThrows(NullPointerException.class, () -> jsonObject.get("nonKey").getAsString());

        assertEquals("Should convert the boolean to string", "true", jsonObject.get("trueKey").getAsString());

    }

    @Test
    public void jsonObjectParseControlCharacters(){
        for(int i = 0;i<=0x001f;i++){
            final String charString = String.valueOf((char)i);
            final String source = "{\"key\":\""+charString+"\"}";
            try {
                JsonObject jo = JsonParser.parseString(source).getAsJsonObject();
                assertEquals("Expected " + charString + "(" + i + ") in the JSON Object but did not find it.", charString, jo.get("key").getAsString());
            } catch (Exception ex) {
                assertTrue("Only \\0 (U+0000), \\n (U+000A), and \\r (U+000D) should cause an error. Instead "+charString+"("+i+") caused an error",
                        i=='\0' || i=='\n' || i=='\r'
                );
            }
        }
    }

    /**
     * Exercise some JSONObject get[type] and opt[type] methods
     */
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
        JsonObject jsonObject = JsonParser.parseString(str).getAsJsonObject();
        assertEquals("stringKey should be string", "hello world!", jsonObject.get("stringKey").getAsString());
    }

}