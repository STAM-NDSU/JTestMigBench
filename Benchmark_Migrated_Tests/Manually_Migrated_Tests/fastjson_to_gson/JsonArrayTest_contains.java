package com.google.gson.migration;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class JsonArrayTest_contains {

    @Test
    public void test_1() {
        JsonArray array = new JsonArray(3);
        assertTrue(array.isEmpty());
        array.add(1);
        assertFalse(array.isEmpty());
        assertTrue(array.contains(new JsonPrimitive(1)));
    }

}
