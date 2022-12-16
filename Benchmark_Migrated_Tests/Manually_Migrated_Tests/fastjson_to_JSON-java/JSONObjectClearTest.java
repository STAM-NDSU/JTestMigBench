package org.json.junit;

import junit.framework.TestCase;
import org.json.JSONObject;
import org.junit.Assert;

public class JSONObjectClearTest extends TestCase {
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

        json.put("E", 99);
        Assert.assertEquals(3, json.toMap().values().size());
        json.clear();
        Assert.assertEquals(0, json.toMap().values().size());
    }
}
