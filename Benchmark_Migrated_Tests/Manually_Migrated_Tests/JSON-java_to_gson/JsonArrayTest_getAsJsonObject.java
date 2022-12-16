package com.google.gson;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class JsonArrayTest_getAsJsonObject {
    @Test
    public void getArrayValues() {
        String arrayStr =
                "[" +
                        "true," +
                        "false," +
                        "\"true\"," +
                        "\"false\"," +
                        "\"hello\"," +
                        "23.45e-4," +
                        "\"23.45\"," +
                        "42," +
                        "\"43\"," +
                        "[" +
                        "\"world\"" +
                        "]," +
                        "{" +
                        "\"key1\":\"value1\"," +
                        "\"key2\":\"value2\"," +
                        "\"key3\":\"value3\"," +
                        "\"key4\":\"value4\"" +
                        "}," +
                        "0," +
                        "\"-1\"" +
                        "]";

        final JsonArray jsonArray = JsonParser.parseString(arrayStr).getAsJsonArray();
        JsonObject nestedJsonObject = jsonArray.get(10).getAsJsonObject();
        assertFalse("Array value JSONObject", nestedJsonObject.isJsonNull());
    }

    @Test
    public void failedGetArrayValues() {
        String arrayStr =
                "[" +
                        "true," +
                        "false," +
                        "\"true\"," +
                        "\"false\"," +
                        "\"hello\"," +
                        "23.45e-4," +
                        "\"23.45\"," +
                        "42," +
                        "\"43\"," +
                        "[" +
                        "\"world\"" +
                        "]," +
                        "{" +
                        "\"key1\":\"value1\"," +
                        "\"key2\":\"value2\"," +
                        "\"key3\":\"value3\"," +
                        "\"key4\":\"value4\"" +
                        "}," +
                        "0," +
                        "\"-1\"" +
                        "]";

        final JsonArray jsonArray = JsonParser.parseString(arrayStr).getAsJsonArray();
        assertThrows(IllegalStateException.class, () -> jsonArray.get(4).getAsJsonObject());
    }

}
