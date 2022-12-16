package org.json;

import junit.framework.TestCase;
import org.junit.Assert;

import java.math.BigDecimal;

public class JSONArrayGetBigDecimalTest extends TestCase {
    public void test_2() throws Exception {
        JSONArray array = new JSONArray();
        array.put(123);
        array.put("222");
        array.put(3);
        array.put(true);
        array.put("true");
        array.put((Object) null);

        Assert.assertEquals(new BigDecimal("123"), array.getBigDecimal(0));
        Assert.assertEquals(new BigDecimal("222"), array.getBigDecimal(1));

        try {
            array.getBigDecimal(5);
            fail();
        } catch (Exception exception) {  }
    }
}
