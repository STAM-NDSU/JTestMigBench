package org.json;

import junit.framework.TestCase;
import org.junit.Assert;

import java.io.StringWriter;

public class JSONWriterEndObjectTest extends TestCase {
    public void test_writer() throws Exception {
        StringWriter out = new StringWriter();
        JSONWriter writer = new JSONWriter(out);
        writer.object();
        writer.key("a");
        writer.value("1");

        writer.key("b");
        writer.value("2");

        writer.key("c");
        writer.value("3");

        writer.endObject();

        Assert.assertEquals("{\"a\":\"1\",\"b\":\"2\",\"c\":\"3\"}", out.toString());
    }
}
