package org.json;

import junit.framework.TestCase;
import org.junit.Assert;

public class JSONArrayGetIntTest extends TestCase {
    public void test_2() throws Exception {
        JSONArray array = new JSONArray();
        array.put(123);
        array.put("222");
        array.put(3);
        array.put(true);
        array.put("true");
        array.put((Object) null);

        Assert.assertEquals(123, array.getInt(0));
        Assert.assertEquals(222, array.getInt(1));

        try {
            array.getInt(5);
            fail();
        } catch (Exception exception) {  }
    }
}