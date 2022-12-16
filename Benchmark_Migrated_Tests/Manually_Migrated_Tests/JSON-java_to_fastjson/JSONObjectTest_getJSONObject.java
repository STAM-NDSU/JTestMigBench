package com.alibaba;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import static org.junit.Assert.*;

public class JSONObjectTest_getJSONObject {

    @Test
    public void testObjectToBigDecimal() {
        double value = 1412078745.01074;
        JSONArray array = JSONArray.parseArray("[{\"value\": " + value + "}]");
        JSONObject jsonObject = array.getJSONObject(0);

        BigDecimal current = jsonObject.getBigDecimal("value");
        BigDecimal wantedValue = BigDecimal.valueOf(value);

        assertEquals(current, wantedValue);
    }

    /**
     * Tests the exception portions of populateMap.
     */
    @Test
    public void testExceptionalBean() {
        ExceptionalBean bean = new ExceptionalBean();
        assertThrows(JSONException.class, () -> JSON.toJSON(bean));
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
        JSONObject jsonObject = JSONObject.parseObject(str);
        JSONObject jsonObjectInner = jsonObject.getJSONObject("objectKey");
        assertTrue("objectKey should be JSONObject",
                jsonObjectInner.get("myKey").equals("myVal"));
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
        JSONObject jsonObject = JSONObject.parseObject(str);
        assertNull(jsonObject.getJSONObject("nonKey"));
        assertThrows(JSONException.class, () -> jsonObject.getJSONObject("stringKey"));
    }



    private class ExceptionalBean {
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

}

