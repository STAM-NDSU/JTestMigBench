package com.google.gson.migration;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.internal.matchers.Null;

import java.math.BigDecimal;

public class JsonArrayTest_getAsBigDecimal {

    @Test
    public void test_2() throws Exception {
        JsonArray array = new JsonArray();
        array.add(123);
        array.add("222");
        array.add(3);
        array.add(true);
        array.add("true");
        array.add(JsonNull.INSTANCE);

        Assert.assertEquals(new BigDecimal("123"), array.get(0).getAsBigDecimal());

        Assert.assertEquals(new BigDecimal("222"), array.get(1).getAsBigDecimal());

        Assert.assertThrows(
                UnsupportedOperationException.class,
                () -> array.get(5).getAsBigDecimal()
        );
    }

}
