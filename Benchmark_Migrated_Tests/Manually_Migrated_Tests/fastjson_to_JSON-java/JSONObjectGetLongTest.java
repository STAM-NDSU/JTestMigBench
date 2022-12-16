package org.json.junit;

import junit.framework.TestCase;
import org.json.JSONObject;
import org.junit.Assert;

import java.util.Date;

public class JSONObjectGetLongTest extends TestCase {
    public void test_getLong() throws Exception {
        JSONObject json = new JSONObject(true);
        json.put("A", 55L);
        json.put("B", 55);
        json.put("K", true);
        Assert.assertEquals(json.getLong("A"), 55L);
        Assert.assertEquals(json.getLong("B"), 55L);

        try {
            json.getLong("C");
            fail();
        } catch (Exception exception) {  }
        Assert.assertEquals(json.getBoolean("K"), true);
        Assert.assertEquals(json.getBoolean("K"), Boolean.TRUE);
    }

    public void test_getLong_1() throws Exception {
        JSONObject json = new JSONObject(false);
        json.put("A", 55L);
        json.put("B", 55);
        Assert.assertEquals(json.getLong("A"), 55L);
        Assert.assertEquals(json.getLong("B"), 55L);

        try {
            json.getLong("C");
            fail();
        } catch (Exception exception) {  }
    }

    public void test_getDate() throws Exception {
        long currentTimeMillis = System.currentTimeMillis();
        JSONObject json = new JSONObject();
        json.put("A", new Date(currentTimeMillis));
        json.put("B", currentTimeMillis);

        try {
            json.getLong("C");
            fail();
        } catch (Exception exception) {  }
    }

    public void test_getBoolean() throws Exception {
        JSONObject json = new JSONObject();
        json.put("A", true);

        try {
            json.getLong("C");
            fail();
        } catch (Exception exception) {  }
    }

    public void test_all_2() throws Exception {
        JSONObject array = new JSONObject();
        array.put("0", 123);
        array.put("1", "222");
        array.put("2", 3);
        array.put("3", true);
        array.put("4", "true");
        array.put("5", "2.0");

        Assert.assertEquals(222L, array.getLong("1"));

    }
}
