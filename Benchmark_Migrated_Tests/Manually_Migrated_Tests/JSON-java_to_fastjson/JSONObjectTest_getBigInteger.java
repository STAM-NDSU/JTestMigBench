package com.alibaba;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class JSONObjectTest_getBigInteger {

    @SuppressWarnings("boxing")
    @Test
    public void bigNumberOperations() {
        BigInteger bigInteger = new BigInteger("123456789012345678901234567890");
        BigDecimal bigDecimal = new BigDecimal(
                "123456789012345678901234567890.12345678901234567890123456789");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("bigInt", bigInteger);
        assertTrue("jsonObject.put() handles bigInt correctly",
                jsonObject.get("bigInt").equals(bigInteger));
        assertTrue("jsonObject.getBigInteger() handles bigInt correctly",
                jsonObject.getBigInteger("bigInt").equals(bigInteger));
        assertTrue("jsonObject serializes bigInt correctly",
                jsonObject.toString().equals("{\"bigInt\":123456789012345678901234567890}"));
        assertTrue("BigInteger as BigDecimal",
                jsonObject.getBigDecimal("bigInt").equals(new BigDecimal(bigInteger)));


        jsonObject = new JSONObject();
        jsonObject.put("bigDec", bigDecimal);
        assertTrue("jsonObject.put() handles bigDec correctly",
                jsonObject.get("bigDec").equals(bigDecimal));
        assertTrue("jsonObject.getBigDecimal() handles bigDec correctly",
                jsonObject.getBigDecimal("bigDec").equals(bigDecimal));
        assertTrue("jsonObject serializes bigDec correctly",
                jsonObject.toString().equals(
                        "{\"bigDec\":123456789012345678901234567890.12345678901234567890123456789}"));

        assertTrue("BigDecimal as BigInteger",
                jsonObject.getBigInteger("bigDec").equals(bigDecimal.toBigInteger()));

        assertNull(jsonObject.getBigDecimal("bigInt"));

        jsonObject.put("stringKey",  "abc");
        try {
            jsonObject.getBigDecimal("stringKey");
            fail("expected an exeption");
        } catch (NumberFormatException ignored) {}

    }

}

