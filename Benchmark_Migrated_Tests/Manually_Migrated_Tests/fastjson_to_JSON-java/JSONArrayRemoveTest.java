package org.json;

import junit.framework.TestCase;
import org.junit.Assert;

import java.util.Arrays;

public class JSONArrayRemoveTest extends TestCase {
    public void test_1() throws Exception {
        JSONArray array = new JSONArray(3);

        array.put(0, 4);
        Assert.assertEquals(4, array.get(0));
        array.put(0, 4);
        Assert.assertEquals(4, array.get(0));
        array.remove(0);
        array.remove(0);
        Assert.assertEquals(0, array.length());
        array.putAll(Arrays.asList(1, 2, 3, 4, 5, 4, 3));
        Assert.assertEquals(2, array.toList().indexOf(3));
        Assert.assertEquals(6, array.toList().lastIndexOf(3));

    }
}
