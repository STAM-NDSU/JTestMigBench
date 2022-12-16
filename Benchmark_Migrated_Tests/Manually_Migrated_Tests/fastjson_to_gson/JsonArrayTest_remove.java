package com.google.gson.migration;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class JsonArrayTest_remove {

    @Test
    public void test_1() {
        JsonArray array = new JsonArray(3);
        Assert.assertTrue(array.isEmpty());
        array.add(1);
        Assert.assertFalse(array.isEmpty());
        Assert.assertTrue(array.contains(new JsonPrimitive(1)));
        Assert.assertEquals(1, array.get(0).getAsInt());
        Assert.assertTrue(array.remove(new JsonPrimitive(1)));
        Assert.assertTrue(array.isEmpty());
        for (int size = array.size(); size > 0; size--) {
            array.remove(size);
        }
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
    }

}
