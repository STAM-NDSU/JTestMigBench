package com.google.gson;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class JsonArrayTest_remove {

    @Test
    public void toList() {
        String jsonArrayStr =
                "[" +
                        "[1,2," +
                        "{\"key3\":true}" +
                        "]," +
                        "{\"key1\":\"val1\",\"key2\":" +
                        "{\"key2\":null}," +
                        "\"key3\":42,\"key4\":[]" +
                        "}," +
                        "[" +
                        "[\"value1\",2.1]" +
                        "," +
                        "[null]" +
                        "]" +
                        "]";

        final JsonArray jsonArray = JsonParser.parseString(jsonArrayStr).getAsJsonArray();
        List<JsonElement> list = new ArrayList<>();
        jsonArray.iterator().forEachRemaining(list::add);

        // assert that the new list is mutable
        assertFalse("Removing an entry should succeed", list.remove(2).isJsonNull());
        assertEquals("List should have 2 elements", 2, list.size());
    }

    @Test
    public void remove() {
        String arrayStr1 =
                "["+
                        "1"+
                        "]";
        final JsonArray jsonArray = JsonParser.parseString(arrayStr1).getAsJsonArray();
        jsonArray.remove(0);
        assertTrue("jsonArray should be empty", jsonArray.isEmpty());
        assertThrows(IndexOutOfBoundsException.class, () -> jsonArray.remove(5));
    }

}
