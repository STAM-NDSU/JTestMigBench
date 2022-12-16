package org.json.junit;

import junit.framework.TestCase;
import org.json.JSONObject;
import org.junit.Assert;

public class JSONObjectIsEmptyTest extends TestCase {
    public void test_all() throws Exception {
        JSONObject json = new JSONObject();
        Assert.assertEquals(true, json.isEmpty());
        json.put("C", 51L);
        json.put("B", 52);
        json.put("A", 53);
        Assert.assertEquals(false, json.isEmpty());
    }
}
