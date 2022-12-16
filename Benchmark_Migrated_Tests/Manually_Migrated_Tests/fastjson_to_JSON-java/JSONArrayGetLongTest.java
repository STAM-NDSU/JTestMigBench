package org.json;

import junit.framework.TestCase;
import org.junit.Assert;

public class JSONArrayGetLongTest extends TestCase {
    public void test_2() throws Exception {
        JSONArray array = new JSONArray();
        array.put(123);
        array.put("222");
        array.put(3);
        array.put(true);
        array.put("true");
        array.put((Object) null);

        Assert.assertEquals(222L, array.getLong(1));

        try {
            array.getLong(5);
            fail();
        } catch (Exception exception) {  }
    }
}
