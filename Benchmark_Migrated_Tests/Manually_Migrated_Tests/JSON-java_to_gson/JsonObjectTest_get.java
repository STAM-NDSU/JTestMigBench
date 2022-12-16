package com.google.gson;

import org.junit.Test;

import java.io.Closeable;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;

import static org.junit.Assert.*;

public class JsonObjectTest_get {
    /**
     * Tests Number serialization.
     */
    @Test
    public void verifyNumberOutput() {
        JsonElement je = new Gson().toJsonTree(Collections.singletonMap("myNumber", new Fraction(4, 2)));
        JsonObject jsonObject = je.getAsJsonObject();
        final JsonObject myNumber = (JsonObject) (jsonObject.get("myNumber"));
        assertEquals(3, myNumber.size());
    }

    /**
     * Exercise some JsonObject get[type] and opt[type] methods
     */
    @Test
    public void jsonObjectValues() {
        final String str =
                "{" +
                        "\"negZeroKey\":-0.0," +
                        "\"objectKey\":{\"myKey\":\"myVal\"}" +
                        "}";
        JsonElement je = JsonParser.parseString(str);

        assertTrue("get negZeroKey should be a JsonPrimitive", je.getAsJsonObject().get("negZeroKey").isJsonPrimitive());

        JsonObject objectKey = je.getAsJsonObject().get("objectKey").getAsJsonObject();
        assertEquals("objectKey should be JsonObject", "myVal", objectKey.get("myKey").getAsString());
    }

    /**
     * This test documents numeric values which could be numerically
     * handled as BigDecimal or BigInteger. It helps determine what outputs
     * will change if those types are supported.
     */
    @Test
    public void jsonValidNumberValuesNeitherLongNorIEEE754Compatible() {
        final String str =
                "{"+
                        "\"numberWithDecimals\":299792.457999999984,"+
                        "\"largeNumber\":12345678901234567890,"+
                        "\"preciseNumber\":0.2000000000000000111,"+
                        "\"largeExponent\":-23.45e2327"+
                        "}";
        final JsonObject jsonObject = JsonParser.parseString(str).getAsJsonObject();
        assertEquals("numberWithDecimals currently evaluates to double 299792.458",
                new BigDecimal("299792.457999999984"),
                jsonObject.get("numberWithDecimals").getAsBigDecimal());

        assertEquals("largeNumber currently evaluates to BigInteger",
                new BigInteger("12345678901234567890"),
                jsonObject.get( "largeNumber" ).getAsBigInteger());
        assertEquals("largeExponent should evaluate as a BigDecimal",
                new BigDecimal("-23.45e2327"),
                jsonObject.get("largeExponent").getAsBigDecimal());
    }

    /**
     * This test documents how JSON-Java handles invalid numeric input.
     */
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
        JsonObject jsonObject = JsonParser.parseString(str).getAsJsonObject();
        JsonPrimitive obj = jsonObject.get( "hexNumber" ).getAsJsonPrimitive();
        assertFalse( "hexNumber must not be a number (should throw exception!?)", obj.isNumber());
        assertEquals("hexNumber currently evaluates to string", "-0x123", obj.getAsString());
        assertEquals("tooManyZeros currently evaluates to string", "00", jsonObject.get("tooManyZeros").getAsString());
        assertEquals("negativeInfinite currently evaluates to string", "-Infinity", jsonObject.get("negativeInfinite").getAsString());
        assertEquals("negativeNaN currently evaluates to string", "-NaN", jsonObject.get("negativeNaN").getAsString());
        assertEquals("negativeFraction currently evaluates to double -0.01", jsonObject.get("negativeFraction").getAsBigDecimal(), BigDecimal.valueOf(-0.01));
        assertEquals("tooManyZerosFraction currently evaluates to double 0.001", jsonObject.get("tooManyZerosFraction").getAsBigDecimal(), BigDecimal.valueOf(0.001));
        assertEquals("negativeHexFloat currently evaluates to double -3.99951171875", -3.99951171875, jsonObject.get("negativeHexFloat").getAsDouble(), 0.0);
        assertEquals("hexFloat currently evaluates to double 4.9E-324", 4.9E-324, jsonObject.get("hexFloat").getAsDouble(), 0.0);
        assertEquals("floatIdentifier currently evaluates to double 0.1", 0.1, jsonObject.get("floatIdentifier").getAsDouble(), 0.0);
        assertEquals("doubleIdentifier currently evaluates to double 0.1", 0.1, jsonObject.get("doubleIdentifier").getAsDouble(), 0.0);
    }


    /**
     * This test documents an unexpected numeric behavior.
     * A double that ends with .0 is parsed, serialized, then
     * parsed again. On the second parse, it has become an int.
     */
    @Test
    public void unexpectedDoubleToIntConversion() {
        // test with this name was not migrated because this phenomenon is not observed in the target library
    }

    /**
     * Explore how JsonObject handles parsing errors.
     */
    @SuppressWarnings({"boxing", "unused"})
    @Test
    public void jsonObjectParsingErrors() {
        // invalid key
        String str = "{\"myKey\":true, \"myOtherKey\":false}";
        JsonObject jsonObject = JsonParser.parseString(str).getAsJsonObject();
        assertNull(jsonObject.get(null));
    }

    /**
     * JSON null is not the same as Java null. This test examines the differences
     * in how they are handled by JSON-java.
     */
    @Test
    public void jsonObjectNullOperations() {
        // add JsonObject.NULL then convert to string in the manner of XML.toString()
        JsonObject jsonObjectJONull = new JsonObject();
        JsonNull obj = JsonNull.INSTANCE;
        jsonObjectJONull.add("key", obj);

        assertTrue("get() JsonObject.NULL should find JsonObject.NULL", jsonObjectJONull.get("key").isJsonNull());

        // now try it with null
        JsonObject jsonObjectNull = new JsonObject();
        jsonObjectNull.add("key", null);
        try {
            assertTrue(jsonObjectNull.get("key").isJsonNull());
        } catch (Exception ignored) {}
    }

    /**
     * test that validates a singleton can be serialized as a bean.
     */
    @SuppressWarnings("boxing")
    @Test
    public void testSingletonBean() {
        final JsonObject jo = new Gson().toJsonTree(Singleton.getInstance()).getAsJsonObject();
        assertEquals(jo.keySet().toString(), 1, jo.size());
        assertEquals(0, jo.get("someInt").getAsInt());

        // Update the singleton values
        Singleton.getInstance().setSomeInt(42);
        Singleton.getInstance().setSomeString("Something");
        final JsonObject jo2 = new Gson().toJsonTree(Singleton.getInstance()).getAsJsonObject();
        assertEquals(2, jo2.size());
        assertEquals(42, jo2.get("someInt").getAsInt());
        assertEquals("Something", jo2.get("someString").getAsString());

        // ensure our original jo hasn't changed.
        assertEquals(0, jo.get("someInt").getAsInt());
    }

    /**
     * test that validates a singleton can be serialized as a bean.
     */
    @SuppressWarnings("boxing")
    @Test
    public void testSingletonEnumBean() {
//        fail("Custom serializer for SingletonEnum is required, currently the instance gets converted to a string field, and actual enum fields are ignored");
        final JsonObject jo = new Gson().toJsonTree(SingletonEnum.getInstance()).getAsJsonObject();
        assertEquals(jo.keySet().toString(), 1, jo.size());
        assertEquals(0, jo.get("someInt").getAsInt());

        // Update the singleton values
        SingletonEnum.getInstance().setSomeInt(42);
        SingletonEnum.getInstance().setSomeString("Something");
        final JsonObject jo2 = new Gson().toJsonTree(SingletonEnum.getInstance()).getAsJsonObject();
        assertEquals(2, jo2.size());
        assertEquals(42, jo2.get("someInt").getAsInt());
        assertEquals("Something", jo2.get("someString").getAsString());

        // ensure our original jo hasn't changed.
        assertEquals(0, jo.get("someInt").getAsInt());
    }

    /**
     * Test to validate that a generic class can be serialized as a bean.
     */
    @SuppressWarnings("boxing")
    @Test
    public void testGenericBean() {
        GenericBean<Integer> bean = new GenericBean(42);
        final JsonObject jo = new Gson().toJsonTree(bean).getAsJsonObject();
        assertEquals(jo.keySet().toString(), 3, jo.size());
        assertEquals(42, jo.get("genericValue").getAsInt());
    }

    /**
     * Test to validate that a generic class can be serialized as a bean.
     */
    @SuppressWarnings("boxing")
    @Test
    public void testGenericIntBean() {
        GenericBeanInt bean = new GenericBeanInt(42);
        final JsonObject jo = new Gson().toJsonTree(bean).getAsJsonObject();
        assertEquals(jo.keySet().toString(), 4, jo.size());
        assertEquals(42, jo.get("genericValue").getAsInt());
    }

    /**
     * Test to verify <code>key</code> limitations in the JsonObject bean serializer.
     */
    @Test
    public void testWeirdListBean() {
        @SuppressWarnings("boxing")
        WeirdList bean = new WeirdList(42, 43, 44);
        final JsonObject jo = new Gson().toJsonTree(bean).getAsJsonObject();
        assertEquals("Expected 1 key to be mapped. Instead found: "+ jo.keySet(),
                1, jo.size());
        assertNull(jo.get("ALL"));
    }

    /**
     * Tests the exception portions of populateMap.
     */
    @Test
    public void testExceptionalBean() {
        ExceptionalBean bean = new ExceptionalBean();
        final JsonObject jo = new Gson().toJsonTree(bean).getAsJsonObject();
        assertEquals("Expected 1 key to be mapped. Instead found: "+ jo.keySet(),
                0, jo.size());
        assertNull(jo.get("closeable"));
    }


    private static class Fraction extends Number implements Comparable<Fraction> {
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
         * @param numerator   numerator
         * @param denominator denominator
         */
        public Fraction(final BigInteger numerator, final BigInteger denominator) {
            super();
            if (numerator == null || denominator == null) {
                throw new IllegalArgumentException("All values must be non-null");
            }
            if (denominator.compareTo(BigInteger.ZERO) == 0) {
                throw new IllegalArgumentException("Divide by zero");
            }

            final BigInteger n;
            final BigInteger d;
            // normalize fraction
            if (denominator.signum() < 0) {
                n = numerator.negate();
                d = denominator.negate();
            } else {
                n = numerator;
                d = denominator;
            }
            this.numerator = n;
            this.denominator = d;
            if (n.compareTo(BigInteger.ZERO) == 0) {
                this.bigDecimal = BigDecimal.ZERO;
            } else if (n.compareTo(d) == 0) {// i.e. 4/4, 10/10
                this.bigDecimal = BigDecimal.ONE;
            } else {
                this.bigDecimal = new BigDecimal(this.numerator).divide(new BigDecimal(this.denominator),
                        RoundingMode.HALF_EVEN);
            }
        }

        /**
         * @param numerator   numerator
         * @param denominator denominator
         */
        public Fraction(final long numerator, final long denominator) {
            this(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator));
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
            if (this.denominator.compareTo(o.denominator) == 0) {
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

    private static final class Singleton {
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

    private enum SingletonEnum {
        /**
         * the singleton instance.
         */
        INSTANCE;
        /** */
        private int someInt;
        /** */
        private String someString;

        /** single instance. */

        /**
         * @return the singleton instance. In a real application, I'd hope no one did
         *         this to an enum singleton.
         */
        public static SingletonEnum getInstance() {
            return INSTANCE;
        }

        /** */
        private SingletonEnum() {
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
    }

    private static class GenericBean<T extends Number & Comparable<T>> implements MyBean {
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
            return 42;
        }

        @Override
        public Double getDoubleKey() {
            return 4.2;
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

    private interface MyBean {
        Integer getIntKey();
        Double getDoubleKey();
        String getStringKey();
        String getEscapeStringKey();
        Boolean isTrueKey();
        Boolean isFalseKey();
        StringReader getStringReaderKey();
    }

    private static class GenericBeanInt extends GenericBean<Integer> {
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
            return this.genericValue.equals((this.a + 1));
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

    private static class WeirdList {
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

    private static class ExceptionalBean {
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

}