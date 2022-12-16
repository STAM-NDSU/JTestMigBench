package com.alibaba;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.Assert.*;

public class JSONObjectTest_getBigDecimal {

    @SuppressWarnings("boxing")
    @Test
    public void bigNumberOperations() {
        BigInteger bigInteger = new BigInteger("123456789012345678901234567890");
        BigDecimal bigDecimal = new BigDecimal(
                "123456789012345678901234567890.12345678901234567890123456789");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("bigInt", bigInteger);

        assertTrue("BigInteger as BigDecimal",
                jsonObject.getBigDecimal("bigInt").equals(new BigDecimal(bigInteger)));


        jsonObject = new JSONObject();
        jsonObject.put("bigDec", bigDecimal);
        assertTrue("jsonObject.put() handles bigDec correctly",
                jsonObject.get("bigDec").equals(bigDecimal));
        assertTrue("jsonObject.getBigDecimal() handles bigDec correctly",
                jsonObject.getBigDecimal("bigDec").equals(bigDecimal));
        assertNull(jsonObject.getBigDecimal("bigInt"));
        jsonObject.put("stringKey",  "abc");
        try {
            jsonObject.getBigDecimal("stringKey");
            fail("expected an exeption");
        } catch (NumberFormatException ignored) {}
    }

    @Test
    public void testObjectToBigDecimal() {
        double value = 1412078745.01074;

        JSONArray array = (JSONArray) JSONArray.parse("[{\"value\": " + value + "}]");
        JSONObject jsonObject = array.getJSONObject(0);

        BigDecimal current = jsonObject.getBigDecimal("value");
        BigDecimal wantedValue = BigDecimal.valueOf(value);

        assertEquals(current, wantedValue);
    }

}
