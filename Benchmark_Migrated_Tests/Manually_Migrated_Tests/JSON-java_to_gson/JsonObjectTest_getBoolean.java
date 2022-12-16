package com.google.gson;

import org.junit.Test;

import static org.junit.Assert.*;

public class JsonObjectTest_getBoolean {

    /**
     * Exercise some JsonObject get[type] and opt[type] methods
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
        assertTrue("trueKey should be true", jsonObject.get("trueKey").getAsBoolean());
        assertFalse("falseKey should be false", jsonObject.get("falseKey").getAsBoolean());
        assertTrue("trueStrKey should be true", jsonObject.get("trueStrKey").getAsBoolean());
        assertFalse("falseStrKey should be false", jsonObject.get("falseStrKey").getAsBoolean());
    }

    /**
     * Tests how JsonObject get[type] handles incorrect types
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
        assertThrows(NullPointerException.class,
                () -> jsonObject.get("nonKey").getAsBoolean());
        assertFalse("'hello world' should be translated to False as a boolean",
                jsonObject.get("stringKey").getAsBoolean());
    }

}