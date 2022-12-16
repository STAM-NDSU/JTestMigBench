package org.json;

import junit.framework.TestCase;
import org.junit.Assert;

import java.io.StringWriter;
import java.io.Writer;

public class JSONObjectWriteValueTest extends TestCase {
    public void test_1() throws Exception {
        Writer out = new StringWriter();

        JSONObject object = new JSONObject("{\"id\":33}");

        JSONObject.writeValue(out, object, 0, 0);
        Assert.assertEquals("{\"id\":33}", out.toString());
    }

    public void test_2() throws Exception {
        StringWriter out = new StringWriter();

        JSONObject object = new JSONObject("{\"id\":33,\"name\":\"jobs\"}");

        JSONObject.writeValue(out, object, 0, 0);
        Assert.assertEquals("{\"id\":33,\"name\":\"jobs\"}", out.toString());
    }

    public void test_3() throws Exception {
        StringWriter out = new StringWriter();

        JSONObject object = new JSONObject();
        object.put("id", 33);
        object.put("name", "jobs");

        JSONArray array = new JSONArray();
        array.put(new JSONObject());
        array.put(new JSONObject());
        object.put("children",array);

        JSONObject.writeValue(out, object, 0, 0);

        Assert.assertEquals("{\"id\":33,\"name\":\"jobs\",\"children\":[{},{}]}", out.toString());
    }

    public void test_4() throws Exception {
        StringWriter out = new StringWriter();

        JSONArray array = new JSONArray();
        array.put(new JSONObject());
        array.put(new JSONObject());
        array.put(new JSONArray());
        JSONArray subArray = new JSONArray();
        {
            subArray.put(new JSONArray());
            subArray.put(new JSONArray());
            array.put(subArray);
            array.put(1);
        }

        JSONObject.writeValue(out, array, 0, 0);
        System.out.println(out.toString());

        Assert.assertEquals("[{},{},[],[[],[]],1]", out.toString());
    }
}
