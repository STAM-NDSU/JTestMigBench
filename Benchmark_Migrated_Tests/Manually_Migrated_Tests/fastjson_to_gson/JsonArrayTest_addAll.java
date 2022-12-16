package com.google.gson.migration;

import com.google.gson.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class JsonArrayTest_addAll {

    @Test
    public void test_1() {
        JsonArray array = new JsonArray(3);
        assertTrue(array.isEmpty());
        array.add(1);
        assertFalse(array.isEmpty());
        assertTrue(array.contains(new JsonPrimitive(1)));

        assertTrue(array.remove(new JsonPrimitive(1)));
        assertTrue(array.isEmpty());


        array.addAll(new Gson().toJsonTree(Collections.singletonList(1)).getAsJsonArray());
        Assert.assertEquals(1, array.size());
        assertTrue(Stream.of(1).map(JsonPrimitive::new)
                .allMatch(array::remove));

        array.addAll(new Gson().toJsonTree(Arrays.asList(1, 2, 3)).getAsJsonArray());
        Assert.assertEquals(3, array.size());
        Stream.of(2, 1, 0).forEach(array::remove);


        array.addAll(new Gson().toJsonTree(Arrays.asList(1, 2, 3)).getAsJsonArray());
        // retainAll
        for (int i = array.size() - 1; i >= 0; i--) {
            if (!Arrays.asList(1, 2).contains(array.get(i).getAsInt())) {
                array.remove(i);
            }
        }
        Assert.assertEquals(2, array.size());
        // retainAll
        for (int i = array.size() - 1; i >= 0; i--) {
            if (!Arrays.asList(2, 4).contains(array.get(i).getAsInt())) {
                array.remove(i);
            }
        }

        Assert.assertEquals(1, array.size());
        array.set(0, new JsonPrimitive(4));
        Assert.assertEquals(4, array.get(0).getAsInt());
        JsonArray newArray = new JsonArray();
        newArray.add(new JsonPrimitive(4));
        newArray.addAll(array);
        array = newArray;
        Assert.assertEquals(4, array.get(0).getAsInt());
        array.remove(0);
        array.remove(0);
        Assert.assertEquals(0, array.size());


        array.addAll(new Gson().toJsonTree(Arrays.asList(1, 2, 3, 4, 5, 4, 3)).getAsJsonArray());
        assertEquals(3, array.get(2).getAsInt());
    }

}
