package org.json.junit;

import junit.framework.TestCase;
import org.json.JSONObject;
import org.junit.Assert;

import java.util.HashMap;

public class JSONObjectGetJSONObjectTest extends TestCase {
    public void test_getObject_null() throws Exception {
        JSONObject json = new JSONObject();
        json.put("obj", (Object) null);

        try {
            json.getJSONObject("obj");
            fail();
        } catch (Exception exception) {  }
    }

    public void test_getObject() throws Exception {
        JSONObject json = new JSONObject();
        json.put("obj", new JSONObject());

        Assert.assertEquals(0, json.getJSONObject("obj").length());
    }

    public void test_getObject_map() throws Exception {
        JSONObject json = new JSONObject();
        json.put("obj", new HashMap());

        Assert.assertEquals(0, json.getJSONObject("obj").length());
    }
}
