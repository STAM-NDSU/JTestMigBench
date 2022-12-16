package org.json.junit;

import junit.framework.TestCase;
import org.json.JSONObject;
import org.junit.Assert;

import java.math.BigDecimal;

public class JSONObjectGetBigDecimalTest extends TestCase {
    public void test_all() throws Exception {
        JSONObject json = new JSONObject();
        Assert.assertEquals(true, json.isEmpty());
        json.put("C", 51L);
        json.put("B", 52);
        json.put("A", 53);
        Assert.assertEquals(false, json.isEmpty());

        Assert.assertEquals(null, json.remove("D"));
        Assert.assertEquals(51L, json.remove("C"));
        Assert.assertEquals(2, json.keySet().size());
        Assert.assertEquals(new BigDecimal("53"), json.getBigDecimal("A"));
    }

    public void test_all_2() throws Exception {
        JSONObject array = new JSONObject();
        array.put("0", 123);
        array.put("1", "222");
        array.put("2", 3);
        array.put("3", true);
        array.put("4", "true");
        array.put("5", "2.0");

        Assert.assertEquals(new BigDecimal("123"), array.getBigDecimal("0"));
        Assert.assertEquals(new BigDecimal("222"), array.getBigDecimal("1"));
    }
}
