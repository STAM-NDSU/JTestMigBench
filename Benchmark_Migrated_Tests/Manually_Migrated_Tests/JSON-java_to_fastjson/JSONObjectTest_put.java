package com.alibaba;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class JSONObjectTest_put {

    @Test
    public void verifySimilar() {
        final String string1 = "HasSameRef";
        JSONObject obj1 = new JSONObject();
        obj1.put("key1", "abc");
        obj1.put("key2", 2);
        obj1.put("key3", string1);

        JSONObject obj2 = new JSONObject();
        obj2.put("key1", "abc");
        obj2.put("key2", 3);
        obj2.put("key3", string1);

        JSONObject obj3 = new JSONObject();
        obj3.put("key1", "abc");
        obj3.put("key2", 2);
        obj3.put("key3", string1);

        assertFalse("Should eval to false", obj1.equals(obj2));
        assertTrue("Should eval to true", obj1.equals(obj3));

    }

    @Test
    public void verifyNumberOutput(){
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(new MyNumberContainer());
        String actual = jsonObject.toString();
        String expected = "{\"myNumber\":{\"number\":42}}";
        assertEquals("Equal", expected , actual);

        jsonObject = new JSONObject();
        jsonObject.put("myNumber", new MyNumber());
        actual = jsonObject.toString();
        expected = "{\"myNumber\":{\"number\":42}}";
        assertEquals("Equal", expected , actual);

        jsonObject = new JSONObject();
        jsonObject.put("myNumber", new AtomicInteger(42));
        actual = jsonObject.toString();
        expected = "{\"myNumber\":42}";
        assertEquals("Equal", expected , actual);

        jsonObject = new JSONObject();
        jsonObject.put("myNumber", new Fraction(4,2));
        actual = jsonObject.toString();
        expected = "{\"myNumber\":{\"denominator\":2,\"numerator\":4}}"; // valid JSON, bug fixed
        assertEquals("Equal", expected , actual);
    }

    @Test
    public void verifyPutCollection() {
        final JSONObject expected = JSONObject.parseObject("{\"myCollection\":[10]}");

        @SuppressWarnings("rawtypes")
        Collection myRawC = Collections.singleton(10);
        JSONObject jaRaw = new JSONObject();
        jaRaw.put("myCollection", myRawC);

        Collection<Object> myCObj = Collections.singleton(10);
        JSONObject jaObj = new JSONObject();
        jaObj.put("myCollection", myCObj);

        Collection<Integer> myCInt = Collections.singleton(10);
        JSONObject jaInt = new JSONObject();
        jaInt.put("myCollection", myCInt);

        assertEquals(
                "The RAW Collection should give me the same as the Typed Collection",
                expected.toString(), jaRaw.toString());
        assertEquals(
                "The RAW Collection should give me the same as the Typed Collection",
                expected.toString(), jaObj.toString());
        assertEquals(
                "The RAW Collection should give me the same as the Typed Collection",
                expected.toString(), jaInt.toString());
    }

    /**
     * Verifies that the put Map has backwards compatibility with RAW types pre-java5.
     */
    @Test
    public void verifyPutMap() {

        final JSONObject expected = JSONObject.parseObject("{\"myMap\":{\"myKey\":10}}");

        @SuppressWarnings("rawtypes")
        Map myRawC = Collections.singletonMap("myKey", Integer.valueOf(10));
        JSONObject jaRaw = new JSONObject();
        jaRaw.put("myMap", myRawC);

        Map<String, Object> myCStrObj = Collections.singletonMap("myKey",
                (Object) Integer.valueOf(10));
        JSONObject jaStrObj = new JSONObject();
        jaStrObj.put("myMap", myCStrObj);

        Map<String, Integer> myCStrInt = Collections.singletonMap("myKey",
                Integer.valueOf(10));
        JSONObject jaStrInt = new JSONObject();
        jaStrInt.put("myMap", myCStrInt);

        Map<?, ?> myCObjObj = Collections.singletonMap((Object) "myKey",
                (Object) Integer.valueOf(10));
        JSONObject jaObjObj = new JSONObject();
        jaObjObj.put("myMap", myCObjObj);

        assertEquals(
                "The RAW Collection should give me the same as the Typed Collection",
                expected.toString(), jaRaw.toString());
        assertEquals(
                "The RAW Collection should give me the same as the Typed Collection",
                expected.toString(), jaStrObj.toString());
        assertEquals(
                "The RAW Collection should give me the same as the Typed Collection",
                expected.toString(), jaStrInt.toString());
        assertEquals(
                "The RAW Collection should give me the same as the Typed Collection",
                expected.toString(), jaObjObj.toString());
    }

    @Test
    public void unexpectedDoubleToIntConversion() {
        String key30 = "key30";
        String key31 = "key31";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(key30, 3.0);
        jsonObject.put(key31, 3.1);

        assertTrue("3.0 should remain a double",
                jsonObject.getDouble(key30) == 3);
        assertTrue("3.1 should remain a double",
                jsonObject.getDouble(key31) == 3.1);
    }

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

        jsonObject.put("stringKey",  "abc");
        try {
            jsonObject.getBigDecimal("stringKey");
            fail("expected an exeption");
        } catch (NumberFormatException ignored) {}


        // bigDec put
        jsonObject = new JSONObject();
        jsonObject.put("bigDec", bigDecimal);
        String actualFromPutStr = jsonObject.toString();
        assertTrue("bigDec from put is a number",
                actualFromPutStr.equals(
                        "{\"bigDec\":123456789012345678901234567890.12345678901234567890123456789}"));
    }

    @Test
    public void jsonObjectPut() {
        String expectedStr =
                "{"+
                        "\"trueKey\":true,"+
                        "\"falseKey\":false,"+
                        "\"arrayKey\":[0,1,2],"+
                        "\"objectKey\":{"+
                        "\"myKey1\":\"myVal1\","+
                        "\"myKey2\":\"myVal2\","+
                        "\"myKey3\":\"myVal3\","+
                        "\"myKey4\":\"myVal4\""+
                        "}"+
                        "}";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("trueKey", true);
        jsonObject.put("falseKey", false);
        Integer [] intArray = { 0, 1, 2 };
        jsonObject.put("arrayKey", Arrays.asList(intArray));

        Map<String, Object> myMap = new HashMap<String, Object>();
        myMap.put("myKey1", "myVal1");
        myMap.put("myKey2", "myVal2");
        myMap.put("myKey3", "myVal3");
        myMap.put("myKey4", "myVal4");
        jsonObject.put("objectKey", myMap);

        // validate JSON
        Object doc = Configuration.defaultConfiguration().jsonProvider().parse(jsonObject.toString());
        assertTrue("expected 4 top level items", ((Map<?,?>)(JsonPath.read(doc, "$"))).size() == 4);



        assertTrue("expected true", Boolean.TRUE.equals(jsonObject.get("trueKey")));
        assertTrue("expected false", Boolean.FALSE.equals(jsonObject.get("falseKey")));
        assertTrue("expected 3 arrayKey items", ((List<?>)(JsonPath.read(doc, "$.arrayKey"))).size() == 3);
        assertTrue("expected 0", Integer.valueOf(0).equals(jsonObject.getJSONArray("arrayKey").get(0)));
        assertTrue("expected 1", Integer.valueOf(1).equals(jsonObject.getJSONArray("arrayKey").get(1)));
        assertTrue("expected 2", Integer.valueOf(2).equals(jsonObject.getJSONArray("arrayKey").get(2)));
        assertTrue("expected 4 objectKey items", ((Map<?,?>)(JsonPath.read(doc, "$.objectKey"))).size() == 4);
        assertTrue("expected myVal1", "myVal1".equals(jsonObject.getJSONObject("objectKey").get("myKey1")));
        assertTrue("expected myVal2", "myVal2".equals(jsonObject.getJSONObject("objectKey").get("myKey2")));
        assertTrue("expected myVal3", "myVal3".equals(jsonObject.getJSONObject("objectKey").get("myKey3")));
        assertTrue("expected myVal4", "myVal4".equals(jsonObject.getJSONObject("objectKey").get("myKey4")));

        jsonObject.remove("trueKey");
        JSONObject expectedJsonObject = JSONObject.parseObject(expectedStr);
        assertFalse("unequal jsonObjects should not be similar", jsonObject.equals(expectedJsonObject));
        assertFalse("jsonObject should not be similar to jsonArray", jsonObject.equals(new JSONArray()));

        String aCompareValueStr = "{\"a\":\"aval\",\"b\":true}";
        String bCompareValueStr = "{\"a\":\"notAval\",\"b\":true}";
        JSONObject aCompareValueJsonObject = JSONObject.parseObject(aCompareValueStr);
        JSONObject bCompareValueJsonObject = JSONObject.parseObject(bCompareValueStr);
        assertTrue("different values should not be similar",
                !aCompareValueJsonObject.equals(bCompareValueJsonObject));

        String aCompareObjectStr = "{\"a\":\"aval\",\"b\":{}}";
        String bCompareObjectStr = "{\"a\":\"aval\",\"b\":true}";
        JSONObject aCompareObjectJsonObject = JSONObject.parseObject(aCompareObjectStr);
        JSONObject bCompareObjectJsonObject = JSONObject.parseObject(bCompareObjectStr);
        assertTrue("different nested JSONObjects should not be similar",
                !aCompareObjectJsonObject.equals(bCompareObjectJsonObject));

        String aCompareArrayStr = "{\"a\":\"aval\",\"b\":[]}";
        String bCompareArrayStr = "{\"a\":\"aval\",\"b\":true}";
        JSONObject aCompareArrayJsonObject = JSONObject.parseObject(aCompareArrayStr);
        JSONObject bCompareArrayJsonObject = JSONObject.parseObject(bCompareArrayStr);
        assertTrue("different nested JSONArrays should not be similar",
                !aCompareArrayJsonObject.equals(bCompareArrayJsonObject));
    }

    @Test
    public void jsonObjectToStringSuppressWarningOnCastToCollection() {
        JSONObject jsonObject = new JSONObject();
        Collection<String> collection = new ArrayList<String>();
        collection.add("abc");
        // ArrayList will be added as an object
        jsonObject.put("key", collection);

        // validate JSON
        Object doc = Configuration.defaultConfiguration().jsonProvider().parse(jsonObject.toString());
        assertTrue("expected 1 top level item", ((Map<?,?>)(JsonPath.read(doc, "$"))).size() == 1);
        assertTrue("expected 1 key item", ((List<?>)(JsonPath.read(doc, "$.key"))).size() == 1);
        assertTrue("expected abc", "abc".equals(jsonObject.getJSONArray("key").get(0)));
    }

    @Test
    public void jsonObjectOptCoercion() {
        JSONObject jo = JSONObject.parseObject("{\"largeNumberStr\":\"19007199254740993.35481234487103587486413587843213584\"}");
        jo.put("largeNumber", new BigDecimal("19007199254740993.35481234487103587486413587843213584"));

        // Test type coercion from larger to smaller
        assertEquals(new BigDecimal("19007199254740993.35481234487103587486413587843213584"), jo.getBigDecimal("largeNumber"));
        assertEquals(new BigInteger("19007199254740993"), jo.getBigInteger("largeNumber"));
        assertEquals(1.9007199254740992E16, jo.getDoubleValue("largeNumber"),0.0);
        assertEquals(1.90071995E16f, jo.getFloatValue("largeNumber"),0.0f);
        assertEquals(19007199254740993L, jo.getLongValue("largeNumber"));
        assertEquals(1874919425, jo.getIntValue("largeNumber"));

        assertEquals(19007199254740992l, (long)Double.parseDouble("19007199254740993.35481234487103587486413587843213584"));
        assertEquals(2147483647, (int)Double.parseDouble("19007199254740993.35481234487103587486413587843213584"));
    }

    /**
     * Verifies that the optBigDecimal method properly converts values to BigDecimal and coerce them consistently.
     */
    @Test
    public void jsonObjectOptBigDecimal() {
        JSONObject jo = new JSONObject()
                .fluentPut("int", 123)
                .fluentPut("long", 654L)
                .fluentPut("float", 1.234f)
                .fluentPut("double", 2.345d)
                .fluentPut("bigInteger", new BigInteger("1234"))
                .fluentPut("bigDecimal", new BigDecimal("1234.56789"))
                .fluentPut("nullVal", null);

        assertEquals(new BigDecimal("123"),jo.getBigDecimal("int"));
        assertEquals(new BigDecimal("654"),jo.getBigDecimal("long"));
        assertEquals(new BigDecimal("1234"),jo.getBigDecimal("bigInteger"));
        assertEquals(new BigDecimal("1234.56789"),jo.getBigDecimal("bigDecimal"));
        assertNull(jo.getBigDecimal("nullVal"));
        assertEquals(jo.getBigDecimal("float"),jo.getBigDecimal("float"));
        assertEquals(jo.getBigDecimal("double"),jo.getBigDecimal("double"));
    }

    /**
     * Verifies that the optBigDecimal method properly converts values to BigDecimal and coerce them consistently.
     */
    @Test
    public void jsonObjectOptBigInteger() {
        JSONObject jo = new JSONObject()
                .fluentPut("int", 123)
                .fluentPut("long", 654L)
                .fluentPut("float", 1.234f)
                .fluentPut("double", 2.345d)
                .fluentPut("bigInteger", new BigInteger("1234"))
                .fluentPut("bigDecimal", new BigDecimal("1234.56789"))
                .fluentPut("nullVal", null);

        assertEquals(new BigInteger("123"),jo.getBigInteger("int"));
        assertEquals(new BigInteger("654"),jo.getBigInteger("long"));
        assertEquals(new BigInteger("1"),jo.getBigInteger("float"));
        assertEquals(new BigInteger("2"),jo.getBigInteger("double"));
        assertEquals(new BigInteger("1234"),jo.getBigInteger("bigInteger"));
        assertEquals(new BigInteger("1234"),jo.getBigInteger("bigDecimal"));
        assertNull(jo.getBigInteger("nullVal"));
    }

    /**
     * Confirm behavior when JSONObject put(key, null object) is called
     */
    @Test
    public void jsonObjectputNull() {
        // put null should remove the item.
        String str = "{\"myKey\": \"myval\"}";
        JSONObject jsonObjectRemove = JSONObject.parseObject(str);
        jsonObjectRemove.remove("myKey");
        assertTrue("jsonObject should be empty", jsonObjectRemove.isEmpty());

        JSONObject jsonObjectPutNull = JSONObject.parseObject(str);
        jsonObjectPutNull.put("myKey", null);
        assertTrue("jsonObject should be not empty", jsonObjectPutNull.size() == 1);
    }

    @Test
    public void jsonObjectNullOperations() {
        JSONObject jsonObjectJONull = new JSONObject();
        jsonObjectJONull.put("key", null);
        Object value = jsonObjectJONull.get("key");
        assertNull("opt() JSONObject.NULL should find JSONObject.NULL", value);

        // now try it with null
        JSONObject jsonObjectNull = new JSONObject();
        jsonObjectNull.put("key", null);
        value = jsonObjectNull.get("key");
        assertNull("opt() null should find null", value);
    }

    @Test
    public void testPutNullBoolean() {
        // null put key
        JSONObject jsonObject = JSONObject.parseObject("{}");
        jsonObject.put(null, false);
        assertEquals("Should have 1 key: [null]", 1, jsonObject.size());
    }

    @Test
    public void testPutNullCollection() {
        // null put key
        JSONObject jsonObject = JSONObject.parseObject("{}");
        jsonObject.put(null, Collections.emptySet());
        assertEquals("Should have 1 key: [null]", 1, jsonObject.size());
    }
    @Test
    public void testPutNullDouble() {
        // null put key
        JSONObject jsonObject = JSONObject.parseObject("{}");
        jsonObject.put(null, 0.0d);
        assertEquals("Should have 1 key: [null]", 1, jsonObject.size());
    }

    @Test
    public void testPutNullFloat() {
        // null put key
        JSONObject jsonObject = JSONObject.parseObject("{}");
        jsonObject.put(null, 0.0f);
        assertEquals("Should have 1 key: [null]", 1, jsonObject.size());
    }

    @Test
    public void testPutNullInt() {
        // null put key
        JSONObject jsonObject = JSONObject.parseObject("{}");
        jsonObject.put(null, 0);
        assertEquals("Should have 1 key: [null]", 1, jsonObject.size());
    }

    @Test
    public void testPutNullLong() {
        // null put key
        JSONObject jsonObject = JSONObject.parseObject("{}");
        jsonObject.put(null, 0L);
        assertEquals("Should have 1 key: [null]", 1, jsonObject.size());
    }
    @Test
    public void testPutNullMap() {
        // null put key
        JSONObject jsonObject = JSONObject.parseObject("{}");
        jsonObject.put(null, Collections.emptyMap());
        assertEquals("Should have 1 key: [null]", 1, jsonObject.size());
    }
    @Test
    public void testPutNullObject() {
        // null put key
        JSONObject jsonObject = JSONObject.parseObject("{}");
        jsonObject.put(null, new Object());
        assertEquals("Should have 1 key: [null]", 1, jsonObject.size());
    }

    @Test
    public void jsonObjectClearMethodTest() {
        //Adds random stuff to the JSONObject
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key1", 123);
        jsonObject.put("key2", "456");
        jsonObject.put("key3", new JSONObject());
        jsonObject.clear(); //Clears the JSONObject
        assertTrue("expected jsonObject.length() == 0", jsonObject.size() == 0); //Check if its length is 0
        jsonObject.getIntValue("key1"); //Should throws org.json.JSONException: JSONObject["asd"] not found
    }


    private class MyNumber extends Number {
        private Number number = BigDecimal.valueOf(42);
        /**
         */
        private static final long serialVersionUID = 1L;

        /**
         * @return number!
         */
        public Number getNumber() {
            return this.number;
        }

        @Override
        public int intValue() {
            return getNumber().intValue();
        }

        @Override
        public long longValue() {
            return getNumber().longValue();
        }

        @Override
        public float floatValue() {
            return getNumber().floatValue();
        }

        @Override
        public double doubleValue() {
            return getNumber().doubleValue();
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         *
         * Number overrides should in general always override the  toString method.
         */
        @Override
        public String toString() {
            return getNumber().toString();
        }

        /* (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((this.number == null) ? 0 : this.number.hashCode());
            return result;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof MyNumber)) {
                return false;
            }
            MyNumber other = (MyNumber) obj;
            if (this.number == null) {
                if (other.number != null) {
                    return false;
                }
            } else if (!this.number.equals(other.number)) {
                return false;
            }
            return true;
        }

    }

    private class MyNumberContainer {
        private MyNumber myNumber = new MyNumber();
        /**
         * @return a MyNumber.
         */
        public Number getMyNumber() {return this.myNumber;}
    }

    private class Fraction extends Number implements Comparable<Fraction> {
        /**
         * serial id.
         */
        private static final long serialVersionUID = 1L;

        /**
         * value as a big decimal.
         */
        private final BigDecimal bigDecimal;

        /**
         * value of the denominator.
         */
        private final BigInteger denominator;
        /**
         * value of the numerator.
         */
        private final BigInteger numerator;

        /**
         * @param numerator
         *            numerator
         * @param denominator
         *            denominator
         */
        public Fraction(final BigInteger numerator, final BigInteger denominator) {
            super();
            if (numerator == null || denominator == null) {
                throw new IllegalArgumentException("All values must be non-null");
            }
            if (denominator.compareTo(BigInteger.ZERO)==0) {
                throw new IllegalArgumentException("Divide by zero");
            }

            final BigInteger n;
            final BigInteger d;
            // normalize fraction
            if (denominator.signum()<0) {
                n = numerator.negate();
                d = denominator.negate();
            } else {
                n = numerator;
                d = denominator;
            }
            this.numerator = n;
            this.denominator = d;
            if (n.compareTo(BigInteger.ZERO)==0) {
                this.bigDecimal = BigDecimal.ZERO;
            } else if (n.compareTo(d)==0) {// i.e. 4/4, 10/10
                this.bigDecimal = BigDecimal.ONE;
            } else {
                this.bigDecimal = new BigDecimal(this.numerator).divide(new BigDecimal(this.denominator),
                        RoundingMode.HALF_EVEN);
            }
        }

        /**
         * @param numerator
         *            numerator
         * @param denominator
         *            denominator
         */
        public Fraction(final long numerator, final long denominator) {
            this(BigInteger.valueOf(numerator),BigInteger.valueOf(denominator));
        }

        /**
         * @return the decimal
         */
        public BigDecimal bigDecimalValue() {
            return this.bigDecimal;
        }

        @Override
        public int compareTo(final Fraction o) {
            // .equals call this, so no .equals compare allowed

            // if they are the same reference, just return equals
            if (this == o) {
                return 0;
            }

            // if my denominators are already equal, just compare the numerators
            if (this.denominator.compareTo(o.denominator)==0) {
                return this.numerator.compareTo(o.numerator);
            }

            // get numerators of common denominators
            // a    x     ay   xb
            // --- --- = ---- ----
            // b    y     by   yb
            final BigInteger thisN = this.numerator.multiply(o.denominator);
            final BigInteger otherN = o.numerator.multiply(this.denominator);

            return thisN.compareTo(otherN);
        }

        @Override
        public double doubleValue() {
            return this.bigDecimal.doubleValue();
        }

        /**
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            final Fraction other = (Fraction) obj;
            return this.compareTo(other) == 0;
        }

        @Override
        public float floatValue() {
            return this.bigDecimal.floatValue();
        }

        /**
         * @return the denominator
         */
        public BigInteger getDenominator() {
            return this.denominator;
        }

        /**
         * @return the numerator
         */
        public BigInteger getNumerator() {
            return this.numerator;
        }

        /**
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (this.bigDecimal == null ? 0 : this.bigDecimal.hashCode());
            return result;
        }

        @Override
        public int intValue() {
            return this.bigDecimal.intValue();
        }

        @Override
        public long longValue() {
            return this.bigDecimal.longValue();
        }

        /**
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return this.numerator + "/" + this.denominator;
        }
    }

}
