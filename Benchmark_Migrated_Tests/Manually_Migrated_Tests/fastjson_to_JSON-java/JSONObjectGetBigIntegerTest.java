package org.json.junit;

import junit.framework.TestCase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;

import java.util.LinkedHashMap;
import java.util.Map;

public class JSONObjectGetBigIntegerTest extends TestCase {
    public void test_0() throws Exception {
        Map<String, Object> map = new LinkedHashMap<String, Object>();

        map.put("a", 1);
        map.put("b", new int[] { 1 });
        map.put("c", new JSONArray());

        JSONObject obj = new JSONObject(map);
        Assert.assertEquals(obj.length(), map.size());

        try {
            obj.getBigInteger("d");
            fail();
        } catch (JSONException exception) {   }

    }
}
