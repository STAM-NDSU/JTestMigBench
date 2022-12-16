package org.json;

import junit.framework.TestCase;
import org.junit.Assert;

import java.math.BigDecimal;

public class JSONObjectObjectToBigDecimalTest extends TestCase {
    public void test_cast_to_BigDecimal_same() throws Exception {
        BigDecimal value = new BigDecimal("123");
        Assert.assertEquals(true, value == new JSONObject().objectToBigDecimal(value, null));
    }
}
