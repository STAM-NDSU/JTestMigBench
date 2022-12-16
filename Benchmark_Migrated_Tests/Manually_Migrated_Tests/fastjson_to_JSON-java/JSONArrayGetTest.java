package org.json;

import junit.framework.TestCase;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class JSONArrayGetTest extends TestCase {
    public void test_constructor() throws Exception {
        List<Object> list = new ArrayList();
        JSONArray array = new JSONArray(list);
        array.put(3);
        Assert.assertEquals(1, array.length());
        Assert.assertEquals(3, array.get(0));
    }
}
