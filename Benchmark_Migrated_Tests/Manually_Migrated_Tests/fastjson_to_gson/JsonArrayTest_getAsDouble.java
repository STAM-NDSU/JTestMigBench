package com.google.gson.migration;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class JsonArrayTest_getAsDouble {

    @Test
    public void test_2() throws Exception {
        JsonArray array = new JsonArray();
        array.add(123);
        array.add("222");
        array.add(3);
        array.add(true);
        array.add("true");
        array.add(JsonNull.INSTANCE);

        Assert.assertEquals(123D, array.get(0).getAsDouble(), 0.0);
    }

}
