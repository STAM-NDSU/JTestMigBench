package com.alibaba.fastjson;

import junit.framework.TestCase;

import java.io.IOException;
import java.io.StringWriter;

public class JSONWriterFlushTest extends TestCase {
    public void testClosedWriterThrowsOnFlush() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JSONWriter writer = new JSONWriter(stringWriter);
        writer.startArray();
        writer.endArray();
        writer.close();
        try {
            writer.flush();
            fail();
        } catch (Exception expected) {
        }
    }
}
