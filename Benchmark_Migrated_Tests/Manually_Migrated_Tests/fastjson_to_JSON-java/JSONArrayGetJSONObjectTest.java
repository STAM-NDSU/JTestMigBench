package org.json;

import junit.framework.TestCase;
import org.junit.Assert;

import java.util.HashMap;

public class JSONArrayGetJSONObjectTest extends TestCase {
    public void test_getObject_null() throws Exception {
        JSONArray array = new JSONArray();
        array.put((Object) null);

        try {
            array.getJSONObject(0);
            fail();
        } catch (Exception exception) {  }
    }

    public void test_getObject() throws Exception {
        JSONArray array = new JSONArray();
        array.put(new JSONObject());

        Assert.assertEquals(0, array.getJSONObject(0).length());
    }

    public void test_getObject_map() throws Exception {
        JSONArray array = new JSONArray();
        array.put(new HashMap());

        Assert.assertEquals(0, array.getJSONObject(0).length());
    }
}
