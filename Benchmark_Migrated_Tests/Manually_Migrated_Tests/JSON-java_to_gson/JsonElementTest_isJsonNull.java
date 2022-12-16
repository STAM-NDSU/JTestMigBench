package com.google.gson;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

public class JsonElementTest_isJsonNull {

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
                        "\"xKey\":null,"+
                        "\"objectKey\":{\"myKey\":\"myVal\"}"+
                        "}";

        final JsonObject asJsonObject = JsonParser.parseString(str).getAsJsonObject();
        assertTrue("xKey should not exist",
                asJsonObject.get("xKey").isJsonNull());
    }

}
