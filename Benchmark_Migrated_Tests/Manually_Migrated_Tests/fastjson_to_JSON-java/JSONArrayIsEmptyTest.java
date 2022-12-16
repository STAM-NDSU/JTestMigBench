package org.json;

import junit.framework.TestCase;
import org.junit.Assert;

public class JSONArrayIsEmptyTest extends TestCase {

    public void test_1() throws Exception {
        JSONArray array = new JSONArray(3);
        Assert.assertEquals(true, array.isEmpty());
        array.put(1);
        Assert.assertEquals(false, array.isEmpty());
        Assert.assertEquals(1, array.remove(0));
        Assert.assertEquals(true, array.isEmpty());
    }
}
