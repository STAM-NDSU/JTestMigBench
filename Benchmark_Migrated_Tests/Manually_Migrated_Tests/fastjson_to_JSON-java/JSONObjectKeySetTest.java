package org.json.junit;

import junit.framework.TestCase;
import org.json.JSONObject;
import org.junit.Assert;

public class JSONObjectKeySetTest extends TestCase {
    public void test_order() throws Exception {
        JSONObject json = new JSONObject(true);
        json.put("C", 55L);
        json.put("B", 55);
        json.put("A", 55);

        Assert.assertEquals("C", json.keySet().toArray()[0]);
        Assert.assertEquals("B", json.keySet().toArray()[1]);
        Assert.assertEquals("A", json.keySet().toArray()[2]);
    }

    public void test_all() throws Exception {
        JSONObject json = new JSONObject();
        Assert.assertEquals(true, json.isEmpty());
        json.put("C", 51L);
        json.put("B", 52);
        json.put("A", 53);
        Assert.assertEquals(false, json.isEmpty());

        Assert.assertEquals(null, json.remove("D"));
        Assert.assertEquals(51L, json.remove("C"));
        Assert.assertEquals(2, json.keySet().size());
    }
}
