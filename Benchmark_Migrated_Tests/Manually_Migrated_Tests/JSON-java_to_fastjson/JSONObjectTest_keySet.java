package com.alibaba;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import org.junit.Test;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

public class JSONObjectTest_keySet {

    @Test
    public void jsonObjectByBean2() {
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(new MyBeanCustomName());
        assertNotNull(jsonObject);
        assertEquals("Wrong number of keys found:",
                5,
                jsonObject.keySet().size());
    }

    /**
     * JSONObject built from a bean that has custom field names inherited from a parent class.
     */
    @Test
    public void jsonObjectByBean3() {
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(new MyBeanCustomNameSubClass());
        assertNotNull(jsonObject);
        assertEquals("Wrong number of keys found:",
                7,
                jsonObject.keySet().size());
    }

    @SuppressWarnings("boxing")
    @Test
    public void testSingletonBean() {
        final JSONObject jo = (JSONObject) JSONObject.toJSON(Singleton.getInstance());
        assertEquals(jo.keySet().toString(), 2, jo.size());
    }

    /**
     * Test to validate that a generic class can be serialized as a bean.
     */
    @SuppressWarnings("boxing")
    @Test
    public void testGenericBean() {
        GenericBean<Integer> bean = new GenericBean(42);
        final JSONObject jo = (JSONObject) JSONObject.toJSON(bean);
        assertEquals(jo.keySet().toString(), 10, jo.size());
    }

    /**
     * Test to validate that a generic class can be serialized as a bean.
     */
    @SuppressWarnings("boxing")
    @Test
    public void testGenericIntBean() {
        GenericBeanInt bean = new GenericBeanInt(42);
        final JSONObject jo = (JSONObject) JSONObject.toJSON(bean);
        assertEquals(jo.keySet().toString(), 12, jo.size());
    }

    /**
     * Test to verify <code>key</code> limitations in the JSONObject bean serializer.
     */
    @Test
    public void testWierdListBean() {
        @SuppressWarnings("boxing")
        WeirdList bean = new WeirdList(42, 43, 44);
        final JSONObject jo = (JSONObject) JSONObject.toJSON(bean);
        assertEquals("Expected 1 key to be mapped. Instead found: "+ jo.keySet(),
                1, jo.size());
    }

    /**
     * Tests the exception portions of populateMap.
     */
    @Test
    public void testExceptionalBean() {
        assertThrows(JSONException.class, () -> JSONObject.toJSON(new ExceptionalBean()));
    }

    private class ExceptionalBean {
        /**
         * @return a closeable.
         */
        public Closeable getCloseable() {
            // anonymous inner class did not work...
            return new ExceptionalBean.MyCloseable();
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
        public final class MyCloseable implements Closeable {

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

    private interface MyBeanCustomNameInterface {
        @JSONField(name = "InterfaceField")
        float getSomeFloat();
        @JSONField(serialize = false)
        int getIgnoredInt();
    }

    private static class MyBeanCustomName implements MyBeanCustomNameInterface {
        public int getSomeInt() { return 42; }
        @JSONField(name = "")
        public long getSomeLong() { return 42L; }
        @JSONField(name = "myStringField")
        public String getSomeString() { return "someStringValue"; }
        @JSONField(name = "Some Weird NAme that Normally Wouldn't be possible!")
        public double getMyDouble() { return 0.0d; }
        @Override
        public float getSomeFloat() { return 2.0f; }
        @Override
        public int getIgnoredInt() { return 40; }
    }

    class MyBeanCustomNameSubClass extends MyBeanCustomName {
        @Override
        @JSONField(name = "forcedInt")
        public int getIgnoredInt() { return 42*42; }
        @Override
        @JSONField(name = "newIntFieldName")
        public int getSomeInt() { return 43; }
        @Override
        public String getSomeString() { return "subClassString"; }
        @Override
        @JSONField(name = "AMoreNormalName")
        public double getMyDouble() { return 1.0d; }
        @Override
        public float getSomeFloat() { return 3.0f; }
        @JSONField(name = "ShouldBeIgnored", serialize = false)
        public boolean getShouldNotBeJSON() { return true; }
        @JSONField(name = "Getable")
        public boolean getable() { return true; }
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
        public static final SingletonEnum getInstance() {
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
}
