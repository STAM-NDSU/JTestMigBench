package com.google.gson.migration;

import com.google.gson.stream.JsonWriter;
import org.junit.Assert;
import org.junit.Test;

import java.io.StringWriter;

public class JsonWriterTest_endObject {

    @Test
    public void test_writer() throws Exception {
        StringWriter out = new StringWriter();
        JsonWriter writer = new JsonWriter(out);
        writer.beginObject();
        writer.value("a");
        writer.value("1");

        writer.value("b");
        writer.value("2");

        writer.value("c");
        writer.value("3");

        writer.endObject();
        writer.close();

        Assert.assertEquals("{\"a\":\"1\",\"b\":\"2\",\"c\":\"3\"}", out.toString());
    }

}
