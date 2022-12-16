package com.google.gson.migration;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JsonArrayTest_isEmpty {

    @Test
    public void test_1() {
        JsonArray array = new JsonArray(3);
        Assert.assertEquals(true, array.isEmpty());
        array.add(1);
        Assert.assertEquals(false, array.isEmpty());
        Assert.assertEquals(true, array.contains(new JsonPrimitive(1)));
        Assert.assertEquals(1, array.get(0).getAsInt());
        Assert.assertEquals(true, array.remove(new JsonPrimitive(1)));
        Assert.assertEquals(true, array.isEmpty());
    }

}
