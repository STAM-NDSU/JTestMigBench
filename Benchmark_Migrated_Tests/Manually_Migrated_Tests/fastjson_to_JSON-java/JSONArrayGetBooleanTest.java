package org.json;

import junit.framework.TestCase;
import org.junit.Assert;

public class JSONArrayGetBooleanTest extends TestCase {
    public void test_2() throws Exception {
        JSONArray array = new JSONArray();
        array.put(123);
        array.put("222");
        array.put(3);
        array.put(true);
        array.put("true");
        array.put((Object) null);

        Assert.assertEquals(Boolean.TRUE, array.getBoolean(4));

        try{
            array.getBoolean(5);
            fail();
        } catch (Exception exception) {  }

    }
}
