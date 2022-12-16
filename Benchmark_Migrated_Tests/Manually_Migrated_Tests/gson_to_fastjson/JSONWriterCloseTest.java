package com.alibaba.fastjson;

import junit.framework.TestCase;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;

public class JSONWriterCloseTest extends TestCase {
    public void testTopLevelValueTypes() throws IOException {
        StringWriter string1 = new StringWriter();
        JSONWriter writer1 = new JSONWriter(string1);
        writer1.writeValue(true);
        writer1.close();
        assertEquals("true", string1.toString());

        StringWriter string2 = new StringWriter();
        JSONWriter writer2 = new JSONWriter(string2);
        writer2.writeValue(null);
        writer2.close();
        assertEquals("null", string2.toString());

        StringWriter string3 = new StringWriter();
        JSONWriter writer3 = new JSONWriter(string3);
        writer3.writeValue(123);
        writer3.close();
        assertEquals("123", string3.toString());

        StringWriter string4 = new StringWriter();
        JSONWriter writer4 = new JSONWriter(string4);
        writer4.writeValue(123.4);
        writer4.close();
        assertEquals("123.4", string4.toString());

        StringWriter string5 = new StringWriter();
        JSONWriter writert = new JSONWriter(string5);
        writert.writeValue("a");
        writert.close();
        assertEquals("\"a\"", string5.toString());
    }

    public void testDoubles() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.startArray();
        jsonWriter.writeValue(-0.0);
        jsonWriter.writeValue(1.0);
        jsonWriter.writeValue(Double.MAX_VALUE);
        jsonWriter.writeValue(Double.MIN_VALUE);
        jsonWriter.writeValue(0.0);
        jsonWriter.writeValue(-0.5);
        jsonWriter.writeValue(2.2250738585072014E-308);
        jsonWriter.writeValue(Math.PI);
        jsonWriter.writeValue(Math.E);
        jsonWriter.endArray();
        jsonWriter.close();
        assertEquals("[-0.0,"
                + "1.0,"
                + "1.7976931348623157E308,"
                + "4.9E-324,"
                + "0.0,"
                + "-0.5,"
                + "2.2250738585072014E-308,"
                + "3.141592653589793,"
                + "2.718281828459045]", stringWriter.toString());
    }

    public void testLongs() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.startArray();
        jsonWriter.writeValue(0);
        jsonWriter.writeValue(1);
        jsonWriter.writeValue(-1);
        jsonWriter.writeValue(Long.MIN_VALUE);
        jsonWriter.writeValue(Long.MAX_VALUE);
        jsonWriter.endArray();
        jsonWriter.close();
        assertEquals("[0,"
                + "1,"
                + "-1,"
                + "-9223372036854775808,"
                + "9223372036854775807]", stringWriter.toString());
    }

    public void testNumbers() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.startArray();
        jsonWriter.writeValue(new BigInteger("0"));
        jsonWriter.writeValue(new BigInteger("9223372036854775808"));
        jsonWriter.writeValue(new BigInteger("-9223372036854775809"));
        jsonWriter.writeValue(new BigDecimal("3.141592653589793238462643383"));
        jsonWriter.endArray();
        jsonWriter.close();
        assertEquals("[0,"
                + "9223372036854775808,"
                + "-9223372036854775809,"
                + "3.141592653589793238462643383]", stringWriter.toString());
    }

    public void testLenientWriterPermitsMultipleTopLevelValues() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JSONWriter writer = new JSONWriter(stringWriter);
        writer.startArray();
        writer.endArray();
        writer.startArray();
        writer.endArray();
        writer.close();
        assertEquals("[][]", stringWriter.toString());
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

    public void testClosedWriterThrowsOnName() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JSONWriter writer = new JSONWriter(stringWriter);
        writer.startArray();
        writer.endArray();
        writer.close();
        try {
            writer.writeKey("a");
            fail();
        } catch (Exception expected) {
        }
    }

    public void testClosedWriterThrowsOnValue() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JSONWriter writer = new JSONWriter(stringWriter);
        writer.startArray();
        writer.endArray();
        writer.close();
        try {
            writer.writeValue("a");
            fail();
        } catch (Exception expected) {
        }
    }

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

    public void testWriterCloseIsIdempotent() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JSONWriter writer = new JSONWriter(stringWriter);
        writer.startArray();
        writer.endArray();
        writer.close();
        writer.close();
    }
}
