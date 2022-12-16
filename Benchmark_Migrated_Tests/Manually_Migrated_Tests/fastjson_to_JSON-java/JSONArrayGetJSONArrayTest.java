package org.json;

import junit.framework.TestCase;
import org.junit.Assert;

import java.util.ArrayList;

public class JSONArrayGetJSONArrayTest extends TestCase {
    public void test_getArray() throws Exception {
        JSONArray array = new JSONArray();
        array.put(new ArrayList());

        Assert.assertEquals(0, array.getJSONArray(0).length());
    }

    public void test_getArray_1() throws Exception {
        JSONArray array = new JSONArray();
        array.put(new JSONArray());

        Assert.assertEquals(0, array.getJSONArray(0).length());
    }
}
