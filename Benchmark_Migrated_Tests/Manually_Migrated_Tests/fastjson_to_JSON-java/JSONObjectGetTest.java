package org.json.junit;

import junit.framework.TestCase;
import org.json.JSONObject;
import org.junit.Assert;

import java.util.LinkedHashMap;
import java.util.Map;

public class JSONObjectGetTest extends TestCase {

    public void test_0() throws Exception {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("a", 1);
        JSONObject obj = new JSONObject(map);

        Assert.assertEquals(obj.length(), map.size());
        Assert.assertEquals(obj.get("a"), map.get("a"));
    }
}
