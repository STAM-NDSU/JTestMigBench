package com.google.gson.migration;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;

import static org.junit.Assert.assertTrue;

public class JsonObjectTest_size {

    @Test
    public void test_getObject() throws Exception {
        final JsonObject json = new JsonObject();
        json.add("obj", new JsonObject());

        Assert.assertEquals(0, json.get("obj").getAsJsonObject().size());
    }

    @Test
    public void test_getObject_map() throws Exception {
        final JsonObject json = new JsonObject();
        json.add("obj", new Gson().toJsonTree(new HashMap()));

        Assert.assertEquals(0, json.get("obj").getAsJsonObject().size());
    }

}
