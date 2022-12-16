package org.json.junit;

import junit.framework.TestCase;
import org.json.JSONObject;
import org.junit.Assert;

import java.util.Collections;

public class JSONObjectGetIntTest extends TestCase {
    public void test_order() throws Exception {
        JSONObject json = new JSONObject(true);
        json.put("C", 55L);
        json.put("B", 55);
        json.put("A", 55);

        try {
            json.getInt("D");
            fail();
        } catch (Exception exception) {  }
    }

    public void test_all() throws Exception {
        JSONObject json = new JSONObject(Collections.singletonMap("E", 99));

        Assert.assertEquals(99, json.getInt("E"));
    }

    public void test_all_2() throws Exception {
        JSONObject array = new JSONObject();
        array.put("0", 123);
        array.put("1", "222");
        array.put("2", 3);
        array.put("3", true);
        array.put("4", "true");
        array.put("5", "2.0");

        Assert.assertEquals(123, array.getInt("0"));
        Assert.assertEquals(222, array.getInt("1"));
    }
}
