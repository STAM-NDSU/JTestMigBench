package com.google.gson.migration;

import com.google.gson.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JsonArrayTest_size {

    @Test
    public void test_1() throws Exception {
        JsonArray array = new JsonArray(3);
        assertTrue(array.isEmpty());
        array.add(1);
        assertTrue(array.remove(new JsonPrimitive(1)));
        array.addAll(new Gson().toJsonTree(singletonList(1)).getAsJsonArray());
        assertEquals(1, array.size());
        array.remove(0);
        assertEquals(0, array.size());
        array.addAll(new Gson().toJsonTree(Arrays.asList(1, 2, 3)).getAsJsonArray());
        assertEquals(3, array.size());
        Stream.of(2, 1, 0).forEach(array::remove);
        array.addAll(new Gson().toJsonTree(Arrays.asList(1, 2, 3)).getAsJsonArray());
        array.remove(2);
        assertEquals(2, array.size());
        array.remove(1);
        array.set(0, new JsonPrimitive(4));
        assertEquals(4, array.get(0).getAsInt());
        JsonArray newArray = new JsonArray();
        newArray.add(new JsonPrimitive(4));
        newArray.addAll(array);
        array = newArray;
        assertEquals(4, array.get(0).getAsInt());
        array.remove(0);
        array.remove(0);
        assertEquals(0, array.size());
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
        List<Object> list = new ArrayList();
        JsonArray array = new JsonArray();
        array.addAll(new Gson().toJsonTree(list).getAsJsonArray());
        array.add(3);
        assertEquals(0, list.size());
        assertEquals(3, array.get(0).getAsInt());
    }

    @Test
    public void test_getJavaBean() throws Exception {
        JsonArray array = JsonParser.parseString("[{id:123, name:'aaa'}]").getAsJsonArray();
        assertEquals(1, array.size());
    }

}
