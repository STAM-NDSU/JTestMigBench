package org.json.junit;

import junit.framework.TestCase;
import org.json.JSONObject;
import org.junit.Assert;

public class JSONObjectGetDoubleTest extends TestCase {
    public void test_all_2() throws Exception {
        JSONObject array = new JSONObject();
        array.put("0", 123);
        array.put("1", "222");
        array.put("2", 3);
        array.put("3", true);
        array.put("4", "true");
        array.put("5", "2.0");

        Assert.assertTrue(2.0D == array.getDouble("5"));
    }
}
