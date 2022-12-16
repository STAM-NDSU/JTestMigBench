package com.google.gson.migration;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JsonObjectTest_entrySet {

    @Test
    public void test() throws Exception {
        JsonObject jsonObject = JsonParser.parseString("{\"test\":null,\"a\":\"cc\"}").getAsJsonObject();
        assertEquals(2, jsonObject.entrySet().size());
        assertTrue(jsonObject.has("test"));
    }

}
