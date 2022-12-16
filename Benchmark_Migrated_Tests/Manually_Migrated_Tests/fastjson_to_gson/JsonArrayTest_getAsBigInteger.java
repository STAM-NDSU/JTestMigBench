package com.google.gson.migration;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonArrayTest_getAsBigInteger {

    @Test
    public void test_0() throws Exception {
        long time = System.currentTimeMillis();
        JsonArray array = new JsonArray();
        array.add(JsonNull.INSTANCE);
        array.add(1);
        array.add(time);
        Assert.assertEquals(new BigInteger("1"), array.get(1).getAsBigInteger());
    }

}
