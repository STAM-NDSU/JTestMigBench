package com.google.gson;

import org.junit.Test;

import java.math.*;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class JsonPrimitiveTest_getAsBigDecimal {

    @SuppressWarnings("boxing")
    @Test
    public void bigNumberOperations() {
        BigInteger bigInteger = new BigInteger("123456789012345678901234567890");
        BigDecimal bigDecimal = new BigDecimal(
                "123456789012345678901234567890.12345678901234567890123456789");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("bigInt", bigInteger);
        assertEquals("BigInteger as BigDecimal", jsonObject.get("bigInt").getAsBigDecimal(), new BigDecimal(bigInteger));


        jsonObject = new JsonObject();
        jsonObject.addProperty("bigDec", bigDecimal);
        assertEquals("jsonObject.getBigDecimal() handles bigDec correctly", jsonObject.get("bigDec").getAsBigDecimal(), bigDecimal);
        assertEquals("BigDecimal as BigInteger", jsonObject.get("bigDec").getAsBigDecimal().toBigInteger(), bigDecimal.toBigInteger());

        try {
            jsonObject.get("bigInt").getAsBigDecimal();
            fail("expected an exeption");
        } catch (NullPointerException ignored) {}

        jsonObject.addProperty("stringKey",  "abc");

        try {
            jsonObject.get("stringKey").getAsBigDecimal();
            fail("expected an exeption");
        } catch (NumberFormatException ignored) {}


        // bigInt,bigDec put
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(bigInteger);
        jsonArray.add(bigDecimal);
        String actualFromPutStr = jsonArray.toString();
        assertEquals("bigInt, bigDec from put is a number", "[123456789012345678901234567890,123456789012345678901234567890.12345678901234567890123456789]", actualFromPutStr);
        assertEquals("getBigDec is bigDec", jsonArray.get(1).getAsBigDecimal(), bigDecimal);
        jsonArray.add(Boolean.TRUE);
        try {
            jsonArray.get(2).getAsBigDecimal();
            fail("should not be able to get big dec");
        } catch (Exception ignored) {}

        // bigInt,bigDec list ctor
        jsonArray = new Gson().toJsonTree(asList(bigInteger, bigDecimal)).getAsJsonArray();
        String actualFromListStr = jsonArray.toString();
        assertEquals("bigInt, bigDec in list is a bigInt, bigDec", "[123456789012345678901234567890,123456789012345678901234567890.12345678901234567890123456789]", actualFromListStr);
        // bigDec bean ctor
        MyBigNumberBean myBigNumberBean = mock(MyBigNumberBean.class);
        when(myBigNumberBean.getBigDecimal()).thenReturn(new BigDecimal("123456789012345678901234567890.12345678901234567890123456789"));
    }

    @Test
    public void jsonObjectOptBigDecimal() {
        JsonObject jo = new JsonObject();
        jo.addProperty("int", 123);
        jo.addProperty("long", 654L);
        jo.addProperty("float", 1.234f);
        jo.addProperty("double", 2.345d);
        jo.addProperty("bigInteger", new BigInteger("1234"));
        jo.addProperty("bigDecimal", new BigDecimal("1234.56789"));
        jo.add("nullVal", JsonNull.INSTANCE);
        assertEquals(new BigDecimal("1.234"), jo.get("float").getAsBigDecimal());
        assertEquals(new BigDecimal("2.345"), jo.get("double").getAsBigDecimal());
    }

    @Test
    public void objectToBigDecimal() {
        double value = 1412078745.01074;
        JsonArray array = JsonParser.parseString("[{\"value\": " + value + "}]").getAsJsonArray();
        JsonObject jsonObject = array.get(0).getAsJsonObject();

        BigDecimal current = jsonObject.get("value").getAsBigDecimal();
        BigDecimal wantedValue = BigDecimal.valueOf(value);

        assertEquals(current, wantedValue);
    }

    interface MyBigNumberBean {
        BigDecimal getBigDecimal();
    }
}