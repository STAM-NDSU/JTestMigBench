package com.alibaba.fastjson;

import junit.framework.TestCase;

import java.io.IOException;
import java.io.StringWriter;


public class JSONWriterEndObjectTest extends TestCase {
    public void testNameWithoutValue() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.startObject();
        jsonWriter.writeKey("a");
        jsonWriter.close();
        try {
            jsonWriter.endObject();
            fail();
        } catch (Exception expected) {
        }
    }

    public void testBadNestingArray() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.startArray();
        jsonWriter.startArray();
        try {
            jsonWriter.endObject();
            fail();
        } catch (Exception expected) {
        }
    }

    public void testNullStringValue() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.startObject();
        jsonWriter.writeKey("a");
        jsonWriter.writeValue((String) null);
        jsonWriter.endObject();
        jsonWriter.close();
        assertEquals("{\"a\":null}", stringWriter.toString());
    }

    public void testJsonValue() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.startObject();
        jsonWriter.writeKey("a");

        JSONObject object = new JSONObject();
        object.put("b", true);
        jsonWriter.writeObject(object);

        jsonWriter.writeKey("c");
        jsonWriter.writeValue(1);
        jsonWriter.endObject();
        jsonWriter.close();
        assertEquals("{\"a\":{\"b\":true},\"c\":1}", stringWriter.toString());
    }

    public void testEmptyObject() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.startObject();
        jsonWriter.endObject();
        jsonWriter.close();
        assertEquals("{}", stringWriter.toString());
    }

    public void testObjectsInArrays() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.startArray();
        jsonWriter.startObject();
        jsonWriter.writeKey("a");
        jsonWriter.writeValue(5);
        jsonWriter.writeKey("b");
        jsonWriter.writeValue(false);
        jsonWriter.endObject();

        jsonWriter.startObject();
        jsonWriter.writeKey("c");
        jsonWriter.writeValue(6);
        jsonWriter.writeKey("d");
        jsonWriter.writeValue(true);
        jsonWriter.endObject();
        jsonWriter.endArray();
        jsonWriter.close();
        assertEquals("[{\"a\":5,\"b\":false},"
                + "{\"c\":6,\"d\":true}]", stringWriter.toString());
    }

    public void testArraysInObjects() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.startObject();
        jsonWriter.writeKey("a");
        jsonWriter.startArray();
        jsonWriter.writeValue(5);
        jsonWriter.writeValue(false);
        jsonWriter.endArray();
        jsonWriter.writeKey("b");
        jsonWriter.startArray();
        jsonWriter.writeValue(6);
        jsonWriter.writeValue(true);
        jsonWriter.endArray();
        jsonWriter.endObject();
        jsonWriter.close();
        assertEquals("{\"a\":[5,false],"
                + "\"b\":[6,true]}", stringWriter.toString());
    }

    public void testDeepNestingObjects() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.startObject();
        for (int i = 0; i < 20; i++) {
            jsonWriter.writeKey("a");
            jsonWriter.startObject();
        }
        for (int i = 0; i < 20; i++) {
            jsonWriter.endObject();
        }
        jsonWriter.endObject();
        jsonWriter.close();
        assertEquals("{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":"
                + "{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{\"a\":{"
                + "}}}}}}}}}}}}}}}}}}}}}", stringWriter.toString());
    }

    public void testRepeatedName() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.startObject();
        jsonWriter.writeKey("a");
        jsonWriter.writeValue(true);
        jsonWriter.writeKey("a");
        jsonWriter.writeValue(false);
        jsonWriter.endObject();
        jsonWriter.close();
        // JsonWriter doesn't attempt to detect duplicate names
        assertEquals("{\"a\":true,\"a\":false}", stringWriter.toString());
    }

    public void testPrettyPrintObject() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JSONWriter jsonWriter = new JSONWriter(stringWriter);

        jsonWriter.startObject();
        jsonWriter.writeKey("a");
        jsonWriter.writeValue(true);
        jsonWriter.writeKey("b");
        jsonWriter.writeValue(false);
        jsonWriter.writeKey("c");
        jsonWriter.writeValue(5.0);
        jsonWriter.writeKey("e");
        jsonWriter.writeValue(null);
        jsonWriter.writeKey("f");
        jsonWriter.startArray();
        jsonWriter.writeValue(6.0);
        jsonWriter.writeValue(7.0);
        jsonWriter.endArray();
        jsonWriter.writeKey("g");
        jsonWriter.startObject();
        jsonWriter.writeKey("h");
        jsonWriter.writeValue(8.0);
        jsonWriter.writeKey("i");
        jsonWriter.writeValue(9.0);
        jsonWriter.endObject();
        jsonWriter.endObject();
        jsonWriter.close();
        String expected = "{"
                + "\"a\":true,"
                + "\"b\":false,"
                + "\"c\":5.0,"
                + "\"e\":null,"
                + "\"f\":["
                + "6.0,"
                + "7.0"
                + "],"
                + "\"g\":{"
                + "\"h\":8.0,"
                + "\"i\":9.0"
                + "}"
                + "}";
        assertEquals(expected, stringWriter.toString());
    }

    public void testPrettyPrintArray() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JSONWriter jsonWriter = new JSONWriter(stringWriter);

        jsonWriter.startArray();
        jsonWriter.writeValue(true);
        jsonWriter.writeValue(false);
        jsonWriter.writeValue(5.0);
        jsonWriter.writeValue(null);
        jsonWriter.startObject();
        jsonWriter.writeKey("a");
        jsonWriter.writeValue(6.0);
        jsonWriter.writeKey("b");
        jsonWriter.writeValue(7.0);
        jsonWriter.endObject();
        jsonWriter.startArray();
        jsonWriter.writeValue(8.0);
        jsonWriter.writeValue(9.0);
        jsonWriter.endArray();
        jsonWriter.endArray();
        jsonWriter.close();

        String expected = "["
                + "true,"
                + "false,"
                + "5.0,"
                + "null,"
                + "{"
                + "\"a\":6.0,"
                + "\"b\":7.0"
                + "},"
                + "["
                + "8.0,"
                + "9.0"
                + "]"
                + "]";
        assertEquals(expected, stringWriter.toString());
    }

    public void testClosedWriterThrowsOnStructure() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JSONWriter writer = new JSONWriter(stringWriter);
        writer.startArray();
        writer.endArray();
        writer.close();
        try {
            writer.startArray();
            fail();
        } catch (Exception expected) {
        }
        try {
            writer.endArray();
            fail();
        } catch (Exception expected) {
        }
        try {
            writer.startObject();
            fail();
        } catch (Exception expected) {
        }
        try {
            writer.endObject();
            fail();
        } catch (Exception expected) {
        }
    }
}
