package com.google.gson;

import org.junit.Test;

import static org.junit.Assert.*;

public class JsonPrimitiveTest_getAsDouble {
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
        assertEquals("doubleKey should be double", -23.45e7, jsonObject.get("doubleKey").getAsDouble(), 0.0);
        assertEquals("doubleStrKey should be double", 1, jsonObject.get("doubleStrKey").getAsDouble(), 0.0);
    }

    @Test
    public void jsonValidNumberValuesNeitherLongNorIEEE754Compatible() {
        // Valid JSON Numbers, probably should return BigDecimal or BigInteger objects
        String str =
                "{"+
                        "\"numberWithDecimals\":299792.457999999984,"+
                        "\"largeNumber\":12345678901234567890,"+
                        "\"preciseNumber\":0.2000000000000000111,"+
                        "\"largeExponent\":-23.45e2327"+
                        "}";
        JsonObject jsonObject = JsonParser.parseString(str).getAsJsonObject();
        // comes back as a double but loses precision
        assertEquals( "preciseNumber currently evaluates to double 0.2",
                0.2, jsonObject.get( "preciseNumber" ).getAsDouble(), 0.0);
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

        assertThrows(NullPointerException.class, () -> jsonObject.get("nonKey").getAsDouble());
        assertThrows(NumberFormatException.class, () -> jsonObject.get("stringKey").getAsDouble());
    }

    /**
     * This test documents an unexpected numeric behavior.
     * A double that ends with .0 is parsed, serialized, then
     * parsed again. On the second parse, it has become an int.
     */
    @Test
    public void unexpectedDoubleToIntConversion() {
        String key30 = "key30";
        String key31 = "key31";
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(key30, 3.0);
        jsonObject.addProperty(key31, 3.1);

        assertEquals("3.0 should remain a double", 3, jsonObject.get(key30).getAsDouble(), 0.0);
        assertEquals("3.1 should remain a double", 3.1, jsonObject.get(key31).getAsDouble(), 0.0);

        // turns 3.0 into 3.
        String serializedString = jsonObject.toString();
        JsonObject deserialized = JsonParser.parseString(serializedString).getAsJsonObject();
        assertEquals("3.0 is now an int", 3, deserialized.get(key30).getAsJsonPrimitive().getAsInt());
        assertEquals("3.0 can still be interpreted as a double",3.0,
                deserialized.get(key30).getAsJsonPrimitive().getAsDouble(), 0.0);
        assertEquals("3.1 remains a double", 3.1, deserialized.get(key31).getAsDouble(), 0.0);
    }
}