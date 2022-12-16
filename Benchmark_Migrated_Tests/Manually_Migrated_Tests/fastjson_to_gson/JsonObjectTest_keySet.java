package com.google.gson.migration;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JsonObjectTest_keySet {

    @Test
    public void test_order() throws Exception {
        final JsonObject json = new JsonObject();
        json.addProperty("C", 55L);
        json.addProperty("B", 55);
        json.addProperty("A", 55);
        Assert.assertEquals("C", json.keySet().toArray()[0]);
        Assert.assertEquals("B", json.keySet().toArray()[1]);
        Assert.assertEquals("A", json.keySet().toArray()[2]);
    }

    @Test
    public void test_all() throws Exception {
        final JsonObject json = new JsonObject();
        Assert.assertEquals(0, json.size());
        json.addProperty("C", 51L);
        json.addProperty("B", 52);
        json.addProperty("A", 53);
        Assert.assertNotEquals(0, json.size());
        assertTrue(json.has("C"));
        Assert.assertFalse(json.has("D"));
        Assert.assertNull(json.remove("D"));
        final JsonElement c = json.remove("C");
        Assert.assertEquals(51L, c.getAsLong());

        Assert.assertEquals(2, json.keySet().size());
    }

}
