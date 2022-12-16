package com.google.gson.migration;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

public class JsonArrayTest_getAsFloat {

    @Test
    public void test_2() throws Exception {
        JsonArray array = new JsonArray();
        array.add(123);
        array.add("222");
        array.add(3);
        array.add(true);
        array.add("true");
        array.add(JsonNull.INSTANCE);

        Assert.assertTrue(123F == array.get(0).getAsFloat());
    }

}
