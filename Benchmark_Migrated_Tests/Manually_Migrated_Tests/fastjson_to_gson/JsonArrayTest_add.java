package com.google.gson.migration;

import com.google.gson.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;

public class JsonArrayTest_add {

    @Test
    public void test_1() throws Exception {
        JsonArray array = new JsonArray();
        assertTrue(array.isEmpty());
        array.add(1);
        assertFalse(array.isEmpty());
        assertTrue(array.contains(new JsonPrimitive(1)));

        assertTrue(array.remove(new JsonPrimitive(1)));
        assertTrue(array.isEmpty());
    }

    @Test
    public void test_2() throws Exception {
        JsonArray array = new JsonArray();
        array.add(123);
        array.add("222");
        array.add(3);
        array.add(true);
        array.add("true");
        array.add(JsonNull.INSTANCE);

        assertEquals(123, array.get(0).getAsByte());
        assertEquals(123, array.get(0).getAsShort());
        assertEquals(123F, array.get(0).getAsFloat(), 0.0);
        assertEquals(123D, array.get(0).getAsDouble(), 0.0);

        assertEquals(123, array.get(0).getAsInt());
        assertEquals(new BigDecimal("123"), array.get(0).getAsBigDecimal());

        assertEquals(222, array.get(1).getAsInt());
        assertEquals(222L, array.get(1).getAsLong());
        assertEquals(new BigDecimal("222"), array.get(1).getAsBigDecimal());

        assertTrue(array.get(4).getAsBoolean());
        assertEquals(Boolean.TRUE, array.get(4).getAsBoolean());

        assertTrue(array.get(5).isJsonNull());
    }

    @Test
    public void test_getObject_null() throws Exception {
        JsonArray array = new JsonArray();
        array.add(JsonNull.INSTANCE);
        assertTrue(array.get(0).isJsonNull());
    }

    @Test
    public void test_getObject() throws Exception {
        JsonArray array = new JsonArray();
        array.add(new JsonObject());
        assertEquals(0, array.get(0).getAsJsonObject().size());
    }

    @Test
    public void test_getObject_map() throws Exception {
        JsonArray array = new JsonArray();
        array.add(new Gson().toJsonTree(new HashMap<>()));
        assertEquals(0, array.get(0).getAsJsonObject().size());
    }

    @Test
    public void test_getArray() throws Exception {
        JsonArray array = new JsonArray();
        array.add(new Gson().toJsonTree(new ArrayList<>()));
        assertEquals(0, array.get(0).getAsJsonArray().size());
    }

    @Test
    public void test_getArray_1() throws Exception {
        JsonArray array = new JsonArray();
        array.add(new JsonArray());
        assertEquals(0, array.get(0).getAsJsonArray().size());
    }

    @Test
    public void test_constructor() throws Exception {
        List<Object> list = new ArrayList<>();
        JsonArray array = new Gson().toJsonTree(list).getAsJsonArray();
        array.add(3);
        assertEquals(0, list.size());
        assertEquals(3, array.get(0).getAsInt());
    }

}
