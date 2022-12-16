package com.google.gson.migration;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonObjectTest_getJSONObject {

    @Test
    public void test_getObject_null() throws Exception {
        JsonObject json = new JsonObject();
        json.add("obj", null);

        Assert.assertTrue(json.get("obj").isJsonNull());
    }

    @Test
    public void test_getObject() throws Exception {
        JsonObject json = new JsonObject();
        json.add("obj", new JsonObject());

        Assert.assertEquals(0, json.get("obj").getAsJsonObject().size());
    }

    @Test
    public void test_getObject_map() throws Exception {
        JsonObject json = new JsonObject();
        json.add("obj", new Gson().toJsonTree(new HashMap<>()));

        Assert.assertEquals(0, json.get("obj").getAsJsonObject().size());
    }

}
