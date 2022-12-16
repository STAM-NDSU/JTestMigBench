package com.google.gson;

import org.junit.Test;

import java.io.*;
import java.math.BigDecimal;

import static org.junit.Assert.*;

public class JsonObjectTest_getAsJsonObject {

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

        JsonObject jsonObjectInner = jsonObject.getAsJsonObject("objectKey");
        assertEquals("objectKey should be JSONObject", "myVal", jsonObjectInner.get("myKey").getAsString());
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

        assertNull("Should be null", jsonObject.getAsJsonObject("nonKey"));

        assertThrows("stringKey value is a JsonPrimitive, it cannot be cast to JsonObject",
                ClassCastException.class, () -> jsonObject.getAsJsonObject("stringKey"));
    }

    @Test
    public void testObjectToBigDecimal() {
        double value = 1412078745.01074;
        Reader reader = new StringReader("[{\"value\": " + value + "}]");
        final JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
        JsonObject jsonObject = array.get(0).getAsJsonObject();

        BigDecimal current = jsonObject.get("value").getAsBigDecimal();
        BigDecimal wantedValue = BigDecimal.valueOf(value);

        assertEquals(current, wantedValue);
    }


}