package com.google.gson;

import org.junit.Test;

import java.math.*;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class JsonPrimitiveTest_getAsBigInteger {

    /**
     * Document behaviors of big numbers. Includes both JsonObject
     * and JSONArray tests
     */
    @SuppressWarnings("boxing")
    @Test
    public void bigNumberOperations() {
        BigInteger bigInteger = new BigInteger("123456789012345678901234567890");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("bigInt", bigInteger);
        assertEquals("jsonObject.put() handles bigInt correctly", bigInteger,
                jsonObject.get("bigInt").getAsBigInteger());
        assertEquals("jsonObject.getBigInteger() handles bigInt correctly",
                bigInteger, jsonObject.get("bigInt").getAsBigInteger());

        BigDecimal bigDecimal = new BigDecimal(
                "123456789012345678901234567890.12345678901234567890123456789");
        jsonObject.addProperty("bigDec", bigDecimal);
        assertEquals("BigDecimal as BigInteger", bigDecimal.toBigInteger(),
                jsonObject.get("bigDec").getAsBigDecimal().toBigInteger());

        // bigInt,bigDec put
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(bigInteger);
        jsonArray.add(bigDecimal);
        String actualFromPutStr = jsonArray.toString();
        assertEquals("bigInt, bigDec from put is a number", "[123456789012345678901234567890,123456789012345678901234567890.12345678901234567890123456789]", actualFromPutStr);
        assertEquals("getBigInt is bigInt", bigInteger, jsonArray.get(0).getAsBigInteger());
        jsonArray.add(Boolean.TRUE);
        try {
            jsonArray.get(2).getAsBigInteger();
            fail("should not be able to get big int");
        } catch (Exception ignored) {}

        // bigInt,bigDec list ctor
        jsonArray = new Gson().toJsonTree(asList(bigInteger, bigDecimal)).getAsJsonArray();
        String actualFromListStr = jsonArray.toString();
        assertEquals("bigInt, bigDec in list is a bigInt, bigDec",
                "[123456789012345678901234567890,123456789012345678901234567890.12345678901234567890123456789]",
                actualFromListStr);
        // bigInt bean ctor
        MyBigNumberBean myBigNumberBean = mock(MyBigNumberBean.class);
        when(myBigNumberBean.getBigInteger()).thenReturn(new BigInteger("123456789012345678901234567890"));

    }

    interface MyBigNumberBean {
        BigInteger getBigInteger();
    }

}