package org.json;

import junit.framework.TestCase;
import org.junit.Assert;

import java.util.Arrays;

public class JSONArrayClearTest extends TestCase {
    public void test_1() throws Exception {
        JSONArray array = new JSONArray(3);

        array.putAll(Arrays.asList(1, 2, 3));
        Assert.assertEquals(3, array.length());
        array.clear();
        array.putAll(Arrays.asList(1, 2, 3));
        Assert.assertEquals(true, array.toList().retainAll(Arrays.asList(1, 2)));
    }
}
