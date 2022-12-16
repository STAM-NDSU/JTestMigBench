package com.google.gson.migration;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class JsonObjectTest_getJSONArray {

    @Test
    public void test_0() throws Exception {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("a", 1);
        map.put("b", new int[] { 1 });
        map.put("c", new JsonArray());

        JsonObject obj = new Gson().toJsonTree(map).getAsJsonObject();

        Assert.assertEquals(obj.size(), map.size());
        Assert.assertEquals(obj.get("a").getAsInt(), map.get("a"));
        JsonArray array = obj.getAsJsonArray("b");
        Assert.assertEquals(array.size(), 1);

        JsonArray array2 = obj.getAsJsonArray("b");
        Assert.assertEquals(array2.size(), 1);
    }

}
