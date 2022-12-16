package com.google.gson.migration;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class JsonObjectTest_get {

    @Test
    public void test_0() throws Exception {
        Map<String, Object> map = new LinkedHashMap<>();
        final JsonObject obj = new Gson().toJsonTree(map).getAsJsonObject();

        Assert.assertEquals(obj.size(), map.size());

        map.put("a", 1);
        // Gson does not keep the reference
        Assert.assertEquals(obj.size() + 1, map.size());
        Assert.assertNull(obj.get("a"));
    }

}
