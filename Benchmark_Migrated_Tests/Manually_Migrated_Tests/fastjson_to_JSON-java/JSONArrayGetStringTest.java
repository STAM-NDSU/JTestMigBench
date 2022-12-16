package org.json;

import junit.framework.TestCase;
import org.junit.Assert;


public class JSONArrayGetStringTest extends TestCase {
    public void test_0() throws Exception {
        long time = System.currentTimeMillis();
        JSONArray array = new JSONArray();
        array.put((Object) null);
        array.put(1);
        array.put(time);

        Assert.assertEquals("1", array.getString(1));
        JSONArray array2 = new JSONArray(array);
        Assert.assertEquals("1", array2.getString(1));
    }
}
