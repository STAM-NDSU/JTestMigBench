package com.google.gson.migration;

import com.google.gson.stream.JsonWriter;
import org.junit.Assert;
import org.junit.Test;

import java.io.StringWriter;

public class JsonWriterTest_endArray {

    @Test
    public void test_writer() throws Exception {
        StringWriter out = new StringWriter();
        JsonWriter writer = new JsonWriter(out);
        writer.beginArray();
        writer.value("1");
        writer.value("2");
        writer.value("3");
        writer.endArray();
        writer.close();

        Assert.assertEquals("[\"1\",\"2\",\"3\"]", out.toString());
    }

}
