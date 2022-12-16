package com.alibaba;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.Assert.*;

public class JSONObjectTest_getDouble {

    @Test
    public void jsonObjectValues() {
        String str =
                "{"+
                        "\"trueKey\":true,"+
                        "\"falseKey\":false,"+
                        "\"trueStrKey\":\"true\","+
                        "\"falseStrKey\":\"false\","+
                        "\"stringKey\":\"hello world!\","+
                        "\"intKey\":42,"+
                        "\"intStrKey\":\"43\","+
                        "\"longKey\":1234567890123456789,"+
                        "\"longStrKey\":\"987654321098765432\","+
                        "\"doubleKey\":-23.45e7,"+
                        "\"doubleStrKey\":\"00001.000\","+
                        "\"BigDecimalStrKey\":\"19007199254740993.35481234487103587486413587843213584\","+
                        "\"negZeroKey\":-0.0,"+
                        "\"negZeroStrKey\":\"-0.0\","+
                        "\"arrayKey\":[0,1,2],"+
                        "\"objectKey\":{\"myKey\":\"myVal\"}"+
                        "}";
        JSONObject jsonObject = (JSONObject) JSON.parse(str);

        assertTrue("doubleKey should be double",
                jsonObject.getDouble("doubleKey") == -23.45e7);
        assertTrue("doubleStrKey should be double",
                jsonObject.getDouble("doubleStrKey") == 1);

    }

    @Test
    public void jsonValidNumberValuesNeitherLongNorIEEE754Compatible() {
        // Valid JSON Numbers, probably should return BigDecimal or BigInteger objects
        String str =
                "{"+
                        "\"numberWithDecimals\":299792.457999999984,"+
                        "\"largeNumber\":12345678901234567890,"+
                        "\"preciseNumber\":0.2000000000000000111,"+
                        "\"largeExponent\":-23.45e2327"+
                        "}";
        JSONObject jsonObject = (JSONObject) JSON.parse(str);
        // Comes back as a double, but loses precision
        assertTrue( "numberWithDecimals currently evaluates to double 299792.458",
                jsonObject.get( "numberWithDecimals" ).equals( new BigDecimal( "299792.457999999984" ) ) );
        Object obj = jsonObject.get( "largeNumber" );
        assertTrue("largeNumber currently evaluates to BigInteger",
                new BigInteger("12345678901234567890").equals(obj));
        // comes back as a double but loses precision
        assertEquals( "preciseNumber currently evaluates to double 0.2",
                0.2, jsonObject.getDouble( "preciseNumber" ), 0.0);
    }

    @Test
    public void jsonObjectNonAndWrongValues() {
        String str =
                "{"+
                        "\"trueKey\":true,"+
                        "\"falseKey\":false,"+
                        "\"trueStrKey\":\"true\","+
                        "\"falseStrKey\":\"false\","+
                        "\"stringKey\":\"hello world!\","+
                        "\"intKey\":42,"+
                        "\"intStrKey\":\"43\","+
                        "\"longKey\":1234567890123456789,"+
                        "\"longStrKey\":\"987654321098765432\","+
                        "\"doubleKey\":-23.45e7,"+
                        "\"doubleStrKey\":\"00001.000\","+
                        "\"arrayKey\":[0,1,2],"+
                        "\"objectKey\":{\"myKey\":\"myVal\"}"+
                        "}";
        JSONObject jsonObject = (JSONObject) JSON.parse(str);
        assertNull(jsonObject.getDouble("nonKey"));
        assertThrows(NumberFormatException.class, () -> jsonObject.getDouble("stringKey"));
    }

    @Test
    public void unexpectedDoubleToIntConversion() {
        String key30 = "key30";
        String key31 = "key31";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(key30, new Double(3.0));
        jsonObject.put(key31, new Double(3.1));

        assertTrue("3.0 should remain a double",
                jsonObject.getDouble(key30) == 3);
        assertTrue("3.1 should remain a double",
                jsonObject.getDouble(key31) == 3.1);

        // turns 3.0 into 3.
        String serializedString = jsonObject.toString();
        JSONObject deserialized = (JSONObject) JSON.parse(serializedString);
        assertTrue("3.0 is now a big decimal", deserialized.get(key30) instanceof BigDecimal);
        assertTrue("3.0 can still be interpreted as a double",
                deserialized.getDouble(key30) == 3.0);
        assertTrue("3.1 remains a double", deserialized.getDouble(key31) == 3.1);
    }

}
