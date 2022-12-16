package org.json.junit;

import junit.framework.TestCase;
import org.json.JSONObject;
import org.junit.Assert;

import java.util.Collections;

public class JSONObjectGetString extends TestCase {
    public void test_all() throws Exception {
        JSONObject json = new JSONObject(Collections.singletonMap("E", "99"));

        Assert.assertEquals("99", json.getString("E"));

        try {
            json.getString("F");
            fail();
        } catch (Exception exception) {  }
    }
}
