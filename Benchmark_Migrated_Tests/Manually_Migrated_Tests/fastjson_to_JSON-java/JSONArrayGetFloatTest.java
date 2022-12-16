package org.json;

import junit.framework.TestCase;
import org.junit.Assert;

public class JSONArrayGetFloatTest extends TestCase {
    public void test_2() throws Exception {
        JSONArray array = new JSONArray();
        array.put(123);
        array.put("222");
        array.put(3);
        array.put(true);
        array.put("true");


        Assert.assertTrue(123F == array.getFloat(0));
    }
}
