package com.alibaba;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.io.Closeable;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class JSONObjectTest_get {

    @Test
    public void verifyNumberOutput(){
        JSONObject jsonObject = (JSONObject) JSON.toJSON(new MyNumberContainer());
        String actual = jsonObject.toString();
        String expected = "{\"myNumber\":{\"number\":42}}";
        assertEquals("Equal", expected , actual);


        jsonObject = new JSONObject();
        jsonObject.put("myNumber", new MyNumber());
        actual = jsonObject.toString();
        expected = "{\"myNumber\":{\"number\":42}}";
        assertEquals("Equal", expected , actual);


        jsonObject = new JSONObject(Collections.singletonMap("myNumber", new AtomicInteger(42)));
        actual = jsonObject.toString();
        expected = "{\"myNumber\":42}";
        assertEquals("Equal", expected , actual);


        jsonObject = new JSONObject();
        jsonObject.put("myNumber", new AtomicInteger(42));
        actual = jsonObject.toString();
        expected = "{\"myNumber\":42}";
        assertEquals("Equal", expected , actual);


        jsonObject = new JSONObject(Collections.singletonMap("myNumber", new Fraction(4,2)));
        assertEquals(1, jsonObject.size());
        final Fraction myNumber = (Fraction) (jsonObject.get("myNumber"));
        assertEquals(2, myNumber.getDenominator().intValue());
        assertEquals(4, myNumber.getNumerator().intValue());
    }

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
        JSONObject jsonObject = JSON.parseObject(str);
        assertTrue("get negZeroKey should be a Double",
                jsonObject.get("negZeroKey") instanceof BigDecimal);
        JSONObject jsonObjectInner = jsonObject.getJSONObject("objectKey");
        assertEquals("objectKey should be JSONObject", "myVal", jsonObjectInner.get("myKey"));
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
        JSONObject jsonObject = JSON.parseObject(str);
        // Comes back as a double, but loses precision
        assertEquals("numberWithDecimals currently evaluates to double 299792.458", jsonObject.get("numberWithDecimals"), new BigDecimal("299792.457999999984"));
        Object obj = jsonObject.get( "largeNumber" );
        assertEquals("largeNumber currently evaluates to BigInteger", new BigInteger("12345678901234567890"), obj);
        // comes back as a double but loses precision
        assertEquals( "preciseNumber currently evaluates to double 0.2",
                0.2, jsonObject.getDouble( "preciseNumber" ), 0.0);
        obj = jsonObject.get( "largeExponent" );
        assertEquals("largeExponent should evaluate as a BigDecimal", new BigDecimal("-23.45e2327"), obj);
    }

    @Test
    public void jsonInvalidNumberValues() {
        // Number-notations supported by Java and invalid as JSON
        String str =
                "{"+
                        "\"hexNumber\":-0x123,"+
                        "\"tooManyZeros\":00,"+
                        "\"negativeInfinite\":-Infinity,"+
                        "\"negativeNaN\":-NaN,"+
                        "\"negativeFraction\":-.01,"+
                        "\"tooManyZerosFraction\":00.001,"+
                        "\"negativeHexFloat\":-0x1.fffp1,"+
                        "\"hexFloat\":0x1.0P-1074,"+
                        "\"floatIdentifier\":0.1f,"+
                        "\"doubleIdentifier\":0.1d"+
                        "}";
        String finalStr = str;
        assertThrows("Should throw syntax error", JSONException.class, () -> JSON.parseObject(finalStr));

        str =
                "{"+
                        "\"hexNumber\":\"-0x123\","+
                        "\"tooManyZeros\":00,"+
                        "\"negativeInfinite\":\"-Infinity\","+
                        "\"negativeNaN\":\"-NaN\","+
                        "\"negativeFraction\":\"-.01\","+
                        "\"tooManyZerosFraction\":00.001,"+
                        "\"negativeHexFloat\":\"-0x1.fffp1\","+
                        "\"hexFloat\":\"0x1.0P-1074\","+
                        "\"floatIdentifier\":0.1,"+
                        "\"doubleIdentifier\":0.1"+
                        "}";
        JSONObject jsonObject = JSON.parseObject(str);
        Object obj;
        obj = jsonObject.get( "hexNumber" );
        assertFalse( "hexNumber must not be a number (should throw exception!?)",
                obj instanceof Number );
        assertEquals("hexNumber currently evaluates to string", "-0x123", obj);
        assertEquals("tooManyZeros currently evaluates to string", 0, jsonObject.get("tooManyZeros"));
        obj = jsonObject.get("negativeInfinite");
        assertEquals("negativeInfinite currently evaluates to string", "-Infinity", obj);
        obj = jsonObject.get("negativeNaN");
        assertEquals("negativeNaN currently evaluates to string", "-NaN", obj);
        assertEquals("negativeFraction currently evaluates to double -0.01", new BigDecimal((String) jsonObject.get("negativeFraction")), BigDecimal.valueOf(-0.01));
        assertEquals("tooManyZerosFraction currently evaluates to double 0.001", jsonObject.get("tooManyZerosFraction"), BigDecimal.valueOf(0.001));
        assertEquals("floatIdentifier currently evaluates to double 0.1", jsonObject.get("floatIdentifier"), BigDecimal.valueOf(0.1));
        assertEquals("doubleIdentifier currently evaluates to double 0.1", jsonObject.get("doubleIdentifier"), BigDecimal.valueOf(0.1));
    }

    @Test
    public void unexpectedDoubleToIntConversion() {
        String key30 = "key30";
        String key31 = "key31";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(key30, 3.0);
        jsonObject.put(key31, 3.1);

        assertEquals("3.0 should remain a double", 3, (double) jsonObject.getDouble(key30), 0.0);
        assertEquals("3.1 should remain a double", 3.1, jsonObject.getDouble(key31), 0.0);

        // turns 3.0 into 3.
        String serializedString = jsonObject.toString();
        JSONObject deserialized = JSON.parseObject(serializedString);
        assertTrue("3.0 is now an BigDecimal", deserialized.get(key30) instanceof BigDecimal);
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


        jsonObject = new JSONObject();
        jsonObject.put("bigDec", bigDecimal);
        assertTrue("jsonObject.put() handles bigDec correctly",
                jsonObject.get("bigDec").equals(bigDecimal));
    }

    @SuppressWarnings({"boxing", "unused"})
    @Test
    public void jsonObjectParsingErrors() {
        // invalid key
        String str = "{\"myKey\":true, \"myOtherKey\":false}";
        JSONObject jsonObject = JSON.parseObject(str);
        assertNull(jsonObject.get(null));
    }

    @Test
    public void jsonObjectNullOperations() {
        // add JSONObject.NULL then convert to string in the manner of XML.toString()
        JSONObject jsonObjectJONull = new JSONObject();
        jsonObjectJONull.put("key", null);
        Object value = jsonObjectJONull.get("key");
        assertNull("get() JSONObject.NULL should find JSONObject.NULL", value);

        // now try it with null
        JSONObject jsonObjectNull = new JSONObject();
        jsonObjectNull.put("key", null);
        assertNull(jsonObjectNull.get("key"));
    }

    @SuppressWarnings("boxing")
    @Test
    public void testSingletonBean() {
        final JSONObject jo = (JSONObject) JSON.toJSON(Singleton.getInstance());
        assertEquals(jo.keySet().toString(), 2, jo.size());
        assertEquals(0, jo.get("someInt"));

        // Update the singleton values
        Singleton.getInstance().setSomeInt(42);
        Singleton.getInstance().setSomeString("Something");
        final JSONObject jo2 = (JSONObject) JSON.toJSON(Singleton.getInstance());
        assertEquals(2, jo2.size());
        assertEquals(42, jo2.get("someInt"));
        assertEquals("Something", jo2.get("someString"));

        // ensure our original jo hasn't changed.
        assertEquals(0, jo.get("someInt"));
    }

    /**
     * Test to validate that a generic class can be serialized as a bean.
     */
    @SuppressWarnings("boxing")
    @Test
    public void testGenericBean() {
        GenericBean<Integer> bean = new GenericBean(42);
        final JSONObject jo = (JSONObject) JSON.toJSON(bean);
        assertEquals(jo.keySet().toString(), 10, jo.size());
        assertEquals(42, jo.get("genericValue"));
        assertEquals("Expected the getter to only be called once",
                1, bean.genericGetCounter);
        assertEquals(0, bean.genericSetCounter);
    }

    /**
     * Test to validate that a generic class can be serialized as a bean.
     */
    @SuppressWarnings("boxing")
    @Test
    public void testGenericIntBean() {
        GenericBeanInt bean = new GenericBeanInt(42);
        final JSONObject jo = (JSONObject) JSON.toJSON(bean);
        assertEquals(jo.keySet().toString(), 12, jo.size());
        assertEquals(42, jo.get("genericValue"));
    }

    /**
     * Test to verify <code>key</code> limitations in the JSONObject bean serializer.
     */
    @Test
    public void testWierdListBean() {
        @SuppressWarnings("boxing")
        WeirdList bean = new WeirdList(42, 43, 44);
        final JSONObject jo = (JSONObject) JSON.toJSON(bean);
        // get() should have a key of 0 length
        // get(int) should be ignored base on parameter count
        // getInt(int) should also be ignored based on parameter count
        // add(Integer) should be ignore as it doesn't start with get/is and also has a parameter
        // getALL should be mapped
        assertEquals("Expected 1 key to be mapped. Instead found: "+jo.keySet().toString(),
                1, jo.size());
        assertNotNull(jo.get("aLL"));
    }

    @Test
    public void testExceptionalBean() {
        ExceptionalBean bean = new ExceptionalBean();
        assertThrows("Should throw Exception", JSONException.class, () -> JSON.toJSON(bean));
    }
}

class ExceptionalBean {
    /**
     * @return a closeable.
     */
    public Closeable getCloseable() {
        // anonymous inner class did not work...
        return new MyCloseable();
    }

    /**
     * @return Nothing really. Just can't be void.
     * @throws IllegalAccessException
     *             always thrown
     */
    public int getIllegalAccessException() throws IllegalAccessException {
        throw new IllegalAccessException("Yup, it's illegal");
    }

    /**
     * @return Nothing really. Just can't be void.
     * @throws IllegalArgumentException
     *             always thrown
     */
    public int getIllegalArgumentException() throws IllegalArgumentException {
        throw new IllegalArgumentException("Yup, it's illegal");
    }

    /**
     * @return Nothing really. Just can't be void.
     * @throws InvocationTargetException
     *             always thrown
     */
    public int getInvocationTargetException() throws InvocationTargetException {
        throw new InvocationTargetException(new Exception("Yup, it's illegal"));
    }

    /** My closeable class. */
    public static final class MyCloseable implements Closeable {

        /**
         * @return a string
         */
        public String getString() {
            return "Yup, it's closeable";
        }

        @Override
        public void close() throws IOException {
            throw new IOException("Closing is too hard!");
        }
    }
}

interface MyBean {
    public Integer getIntKey();
    public Double getDoubleKey();
    public String getStringKey();
    public String getEscapeStringKey();
    public Boolean isTrueKey();
    public Boolean isFalseKey();
    public StringReader getStringReaderKey();
}

class GenericBean<T extends Number & Comparable<T>> implements MyBean {
    /**
     * @param genericValue
     *            value to initiate with
     */
    public GenericBean(T genericValue) {
        super();
        this.genericValue = genericValue;
    }

    /** */
    protected T genericValue;
    /** to be used by the calling test to see how often the getter is called */
    public int genericGetCounter;
    /** to be used by the calling test to see how often the setter is called */
    public int genericSetCounter;

    /** @return the genericValue */
    public T getGenericValue() {
        this.genericGetCounter++;
        return this.genericValue;
    }

    /**
     * @param genericValue
     *            generic value to set
     */
    public void setGenericValue(T genericValue) {
        this.genericSetCounter++;
        this.genericValue = genericValue;
    }

    @Override
    public Integer getIntKey() {
        return Integer.valueOf(42);
    }

    @Override
    public Double getDoubleKey() {
        return Double.valueOf(4.2);
    }

    @Override
    public String getStringKey() {
        return "MyString Key";
    }

    @Override
    public String getEscapeStringKey() {
        return "\"My String with \"s";
    }

    @Override
    public Boolean isTrueKey() {
        return Boolean.TRUE;
    }

    @Override
    public Boolean isFalseKey() {
        return Boolean.FALSE;
    }

    @Override
    public StringReader getStringReaderKey() {
        return new StringReader("Some String Value in a reader");
    }

}

class GenericBeanInt extends GenericBean<Integer> {
    /** */
    final char a = 'A';

    /** @return the a */
    public char getA() {
        return this.a;
    }

    /**
     * Should not be beanable
     *
     * @return false
     */
    public boolean getable() {
        return false;
    }

    /**
     * Should not be beanable
     *
     * @return false
     */
    public boolean get() {
        return false;
    }

    /**
     * Should not be beanable
     *
     * @return false
     */
    public boolean is() {
        return false;
    }

    /**
     * Should be beanable
     *
     * @return false
     */
    public boolean isB() {
        return this.genericValue.equals((Integer.valueOf(this.a+1)));
    }

    /**
     * @param genericValue
     *            the value to initiate with.
     */
    public GenericBeanInt(Integer genericValue) {
        super(genericValue);
    }

    /** override to generate a bridge method */
    @Override
    public Integer getGenericValue() {
        return super.getGenericValue();
    }

}

class MyNumber extends Number {
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

class MyNumberContainer {
    private MyNumber myNumber = new MyNumber();
    /**
     * @return a MyNumber.
     */
    public Number getMyNumber() {return this.myNumber;}
}

class WeirdList {
    /** */
    private final List<Integer> list = new ArrayList();

    /**
     * @param vals
     */
    public WeirdList(Integer... vals) {
        this.list.addAll(Arrays.asList(vals));
    }

    /**
     * @return a copy of the list
     */
    public List<Integer> get() {
        return new ArrayList(this.list);
    }

    /**
     * @return a copy of the list
     */
    public List<Integer> getALL() {
        return new ArrayList(this.list);
    }

    /**
     * get a value at an index.
     *
     * @param i
     *            index to get
     * @return the value at the index
     */
    public Integer get(int i) {
        return this.list.get(i);
    }

    /**
     * get a value at an index.
     *
     * @param i
     *            index to get
     * @return the value at the index
     */
    @SuppressWarnings("boxing")
    public int getInt(int i) {
        return this.list.get(i);
    }

    /**
     * @param value
     *            new value to add to the end of the list
     */
    public void add(Integer value) {
        this.list.add(value);
    }
}

class Singleton {
    /** */
    private int someInt;
    /** */
    private String someString;
    /** single instance. */
    private static final Singleton INSTANCE = new Singleton();

    /** @return the singleton instance. */
    public static final Singleton getInstance() {
        return INSTANCE;
    }

    /** */
    private Singleton() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Already instantiated");
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return INSTANCE;
    }

    /** @return someInt */
    public int getSomeInt() {
        return this.someInt;
    }

    /**
     * sets someInt.
     *
     * @param someInt
     *            the someInt to set
     */
    public void setSomeInt(int someInt) {
        this.someInt = someInt;
    }

    /** @return someString */
    public String getSomeString() {
        return this.someString;
    }

    /**
     * sets someString.
     *
     * @param someString
     *            the someString to set
     */
    public void setSomeString(String someString) {
        this.someString = someString;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.someInt;
        result = prime * result + ((this.someString == null) ? 0 : this.someString.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Singleton other = (Singleton) obj;
        if (this.someInt != other.someInt)
            return false;
        if (this.someString == null) {
            if (other.someString != null)
                return false;
        } else if (!this.someString.equals(other.someString))
            return false;
        return true;
    }
}

class Fraction extends Number implements Comparable<Fraction> {
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