package org.json;

import junit.framework.TestCase;
import org.junit.Assert;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

public class JSONObjectPutTest extends TestCase {
    public void test_getLong() throws Exception {
        JSONObject json = new JSONObject(true);
        json.put("A", (Object) 55L);
        json.put("B", (Object) 55);
        json.put("K", (Object) true);
        Assert.assertEquals(json.getLong("A"), 55L);
        Assert.assertEquals(json.getLong("B"), 55L);
        try {
            json.getLong("C");
            fail();
        } catch (JSONException e) {}
        Assert.assertEquals(json.getBoolean("K"), Boolean.TRUE);
    }

    public void test_getLong_1() throws Exception {
        JSONObject json = new JSONObject(false);
        json.put("A", (Object) 55L);
        json.put("B", (Object) 55);
        Assert.assertEquals(json.getLong("A"), 55L);
        Assert.assertEquals(json.getLong("B"), 55L);
        try {
            json.getLong("C");
            fail();
        } catch (JSONException e) {}
    }

    public void test_getDate() throws Exception {
        long currentTimeMillis = System.currentTimeMillis();
        JSONObject json = new JSONObject();
        json.put("A", new Date(currentTimeMillis));
        json.put("B", (Object) currentTimeMillis);
        Assert.assertEquals(((Date) json.get("A")).getTime(), currentTimeMillis);
        Assert.assertEquals((new Date (json.getLong("B"))).getTime(), currentTimeMillis);
        try {
            json.getLong("C");
            fail();
        } catch (JSONException e) {}
    }

    public void test_getBoolean() throws Exception {
        JSONObject json = new JSONObject();
        json.put("A", (Object) true);
        Assert.assertEquals(json.getBoolean("A"), true);
        try {
            json.getLong("C");
            fail();
        } catch (JSONException e) {}
    }

    public void test_getInt() throws Exception {
        JSONObject json = new JSONObject();
        json.put("A", (Object) 55L);
        json.put("B", (Object) 55);
        Assert.assertEquals(json.getInt("A"), 55);
        Assert.assertEquals(json.getInt("B"), 55);
        try {
            json.getLong("C");
            fail();
        } catch (JSONException e) {}
    }

    public void test_order() throws Exception {
        JSONObject json = new JSONObject(true);
        json.put("C", (Object) 55L);
        json.put("B", (Object) 55);
        json.put("A", (Object) 55);
        Assert.assertEquals("C", json.keySet().toArray()[0]);
        Assert.assertEquals("B", json.keySet().toArray()[1]);
        Assert.assertEquals("A", json.keySet().toArray()[2]);

        Assert.assertEquals(0, json.getInt("D"));
        Assert.assertEquals(0L, json.getLong("D"));
        Assert.assertEquals(false, json.getBoolean("D"));
    }

    public void test_all() throws Exception {
        JSONObject json = new JSONObject();
        Assert.assertEquals(true, json.isEmpty());
        json.put("C", (Object) 51L);
        json.put("B", (Object) 52);
        json.put("A", (Object) 53);
        Assert.assertEquals(false, json.isEmpty());
        Assert.assertEquals(true, json.keySet().contains("C"));
        Assert.assertEquals(false, json.keySet().contains("D"));
        Assert.assertEquals(null, json.remove("D"));
        Assert.assertEquals(51L, json.remove("C"));
        Assert.assertEquals(2, json.keySet().size());
        Assert.assertEquals(2, json.length());
        Assert.assertEquals(new BigDecimal("53"), json.getBigDecimal("A"));
    }

    public void test_all_2() throws Exception {
        JSONObject array = new JSONObject();
        array.put("0", (Object)123);
        array.put("1", "222");
        array.put("2", (Object)3);
        array.put("3", (Object)true);
        array.put("4", "true");
        array.put("5", "2.0");

        Assert.assertEquals(123, array.getInt("0"));
        Assert.assertEquals(123, array.getLong("0"));
        Assert.assertEquals(new BigDecimal("123"), array.getBigDecimal("0"));

        Assert.assertEquals(222, array.getInt("1"));
        Assert.assertEquals(3, ((Integer) array.get("2")).byteValue());
        Assert.assertEquals(3, ((Integer) array.get("2")).shortValue());
        Assert.assertEquals(new Integer(222), (Integer) array.getInt("1"));
        Assert.assertEquals(new Long(222), (Long) array.getLong("1"));
        Assert.assertEquals(new BigDecimal("222"), array.getBigDecimal("1"));

        Assert.assertEquals(true, array.getBoolean("4"));
        Assert.assertTrue(2.0F == array.getFloat("5"));
        Assert.assertTrue(2.0D == array.getDouble("5"));
    }

    public void test_getObject_null() throws Exception {
        JSONObject json = new JSONObject();
        json.put("obj", (Object) null);

        try {
            json.getJSONObject("obj");
            fail();
        } catch (JSONException e) {}
    }

    public void test_getObject_map() throws Exception {
        JSONObject json = new JSONObject();
        json.put("obj", new HashMap());

        Assert.assertEquals(0, json.getJSONObject("obj").length());
    }
}
