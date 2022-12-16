package org.json;

import junit.framework.TestCase;
import org.junit.Assert;

import java.math.BigInteger;


public class JSONObjectObjectToBigIntegerTest extends TestCase {
    public void test_cast_to_BigInteger_same() throws Exception {
        BigInteger value = new BigInteger("123");
        Assert.assertEquals(true, value == new JSONObject().objectToBigInteger(value, null));
    }
}
