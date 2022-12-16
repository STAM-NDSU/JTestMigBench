package org.json.junit;

import junit.framework.TestCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;


public class JSONObjectGetBooleanTest extends TestCase {
    public void test_getLong() throws Exception {
        JSONObject json = new JSONObject(true);
        json.put("A", 55L);
        json.put("B", 55);
        json.put("K", true);

        Assert.assertEquals(json.getBoolean("K"), Boolean.TRUE);
    }

    public void test_getBoolean() throws Exception {
        JSONObject json = new JSONObject();
        json.put("A", true);
        Assert.assertEquals(json.getBoolean("A"), true);
    }

    public void test_all() throws Exception {
        JSONObject json = new JSONObject();
        Assert.assertEquals(true, json.isEmpty());
        json.put("C", 51L);
        json.put("B", 52);
        json.put("A", 53);
        Assert.assertEquals(false, json.isEmpty());
        try {
            json.getBoolean("F");
            fail();
        } catch (JSONException e) {   }
    }
}
