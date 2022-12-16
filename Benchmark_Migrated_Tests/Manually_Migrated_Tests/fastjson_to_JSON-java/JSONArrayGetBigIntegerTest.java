package org.json;

import junit.framework.TestCase;
import org.junit.Assert;

import java.math.BigInteger;

public class JSONArrayGetBigIntegerTest extends TestCase {
    public void test_0() throws Exception {
        long time = System.currentTimeMillis();
        JSONArray array = new JSONArray();
        array.put((Object) null);
        array.put(1);
        array.put(time);

        Assert.assertEquals(new BigInteger("1"), array.getBigInteger(1));
        JSONArray array2 = new JSONArray(array);
        Assert.assertEquals(new BigInteger("1"), array2.getBigInteger(1));
    }
}
