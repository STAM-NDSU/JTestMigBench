package org.json;

import junit.framework.TestCase;
import org.junit.Assert;

import java.io.StringWriter;


public class JSONWriterEndArrayTest extends TestCase {
    public void test_writer() throws Exception {
        StringWriter out = new StringWriter();
        JSONWriter writer = new JSONWriter(out);
        writer.array();
        writer.value("1");
        writer.value("2");
        writer.value("3");
        writer.endArray();

        Assert.assertEquals("[\"1\",\"2\",\"3\"]", out.toString());
    }
}
