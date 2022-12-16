package com.google.gson;

import org.junit.Test;

import static org.junit.Assert.*;

public class JsonPrimitiveTest_getAsLong {

    @Test
    public void jsonObjectValues() {
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
                        "\"BigDecimalStrKey\":\"19007199254740993.35481234487103587486413587843213584\"," +
                        "\"negZeroKey\":-0.0," +
                        "\"negZeroStrKey\":\"-0.0\"," +
                        "\"arrayKey\":[0,1,2]," +
                        "\"objectKey\":{\"myKey\":\"myVal\"}" +
                        "}";
        JsonObject jsonObject = JsonParser.parseString(str).getAsJsonObject();

        assertEquals("longKey should be long", 1234567890123456789L, jsonObject.get("longKey").getAsLong());
        assertEquals("longStrKey should be long", 987654321098765432L, jsonObject.get("longStrKey").getAsLong());
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
        JsonObject jsonObject = JsonParser.parseString(str).getAsJsonObject();
        assertThrows(NullPointerException.class, () -> jsonObject.get("nonKey").getAsLong());
        assertThrows(NumberFormatException.class, () -> jsonObject.get("stringKey").getAsLong());
    }

}