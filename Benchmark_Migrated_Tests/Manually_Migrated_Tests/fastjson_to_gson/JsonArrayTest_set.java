package com.google.gson.migration;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class JsonArrayTest_set {

    @Test
    public void test_1() throws Exception {
        JsonArray array = new JsonArray(3);
        array.add(false);
        array.set(0, new JsonPrimitive(4));
        Assert.assertEquals(4, array.get(0).getAsInt());
    }

}
