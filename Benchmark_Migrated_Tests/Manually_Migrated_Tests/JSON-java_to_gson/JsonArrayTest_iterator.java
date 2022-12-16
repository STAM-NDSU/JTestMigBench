package com.google.gson;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Iterator;

import static org.junit.Assert.*;

public class JsonArrayTest_iterator {

    @Test
    public void iteratorTest() {
        String arrayStr =
                "["+
                        "true,"+
                        "false,"+
                        "\"true\","+
                        "\"false\","+
                        "\"hello\","+
                        "23.45e-4,"+
                        "\"23.45\","+
                        "42,"+
                        "\"43\","+
                        "["+
                        "\"world\""+
                        "],"+
                        "{"+
                        "\"key1\":\"value1\","+
                        "\"key2\":\"value2\","+
                        "\"key3\":\"value3\","+
                        "\"key4\":\"value4\""+
                        "},"+
                        "0,"+
                        "\"-1\""+
                        "]";

        final JsonArray jsonArray = JsonParser.parseString(arrayStr).getAsJsonArray();
        Iterator<JsonElement> it = jsonArray.iterator();
        assertEquals("Array true", Boolean.TRUE, it.next().getAsBoolean());
        assertEquals("Array false", Boolean.FALSE, it.next().getAsBoolean());
        assertEquals("Array string true", "true", it.next().getAsString());
        assertEquals("Array string false", "false", it.next().getAsString());
        assertEquals("Array string", "hello", it.next().getAsString());

        assertEquals("Array double [23.45e-4]", new BigDecimal("0.002345"), it.next().getAsBigDecimal());
        assertEquals("Array string double", 23.45, Double.parseDouble(it.next().getAsString()), 0.0);

        assertEquals("Array value int",
                42, it.next().getAsInt());
        assertEquals("Array value string int", 43, Integer.parseInt(it.next().getAsString()));

        JsonArray nestedJsonArray = it.next().getAsJsonArray();
        assertFalse("Array value JSONArray", nestedJsonArray.isJsonNull());

        JsonObject nestedJsonObject = it.next().getAsJsonObject();
        assertNotNull("Array value JSONObject", nestedJsonObject);

        assertEquals("Array value long", 0L, (it.next().getAsNumber()).longValue());
        assertEquals("Array value string long", (long) -1, Long.parseLong(it.next().getAsString()));
        assertFalse("should be at end of array", it.hasNext());
    }

}
