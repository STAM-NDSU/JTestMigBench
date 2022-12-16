package com.alibaba.fastjson;

import junit.framework.TestCase;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;

public class JSONWriterEndArrayTest extends TestCase {
    public void testMultipleTopLevelValues() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.startArray();
        jsonWriter.endArray();
        jsonWriter.close();
        try {
            jsonWriter.startArray();
            fail();
        } catch (Exception expected) {
        }
    }

    public void testBadNestingObject() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.startArray();
        jsonWriter.startObject();
        try {
            jsonWriter.endArray();
            jsonWriter.close();
            fail();
        } catch (Exception expected) {
        }
    }

    public void testNonFiniteDoublesWhenLenient() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.startArray();
        jsonWriter.writeValue(Double.toString(Double.NaN));
        jsonWriter.writeValue(Double.toString(Double.NEGATIVE_INFINITY));
        jsonWriter.writeValue(Double.toString(Double.POSITIVE_INFINITY));
        jsonWriter.endArray();
        jsonWriter.close();
        assertEquals("[\"NaN\",\"-Infinity\",\"Infinity\"]", stringWriter.toString());
    }

    public void testNonFiniteBoxedDoublesWhenLenient() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.startArray();
        jsonWriter.writeValue(Double.toString(Double.valueOf(Double.NaN)));
        jsonWriter.writeValue(Double.toString(Double.valueOf(Double.NEGATIVE_INFINITY)));
        jsonWriter.writeValue(Double.toString(Double.valueOf(Double.POSITIVE_INFINITY)));
        jsonWriter.endArray();
        jsonWriter.close();
        assertEquals("[\"NaN\",\"-Infinity\",\"Infinity\"]", stringWriter.toString());
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

    public void testBooleans() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.startArray();
        jsonWriter.writeValue(true);
        jsonWriter.writeValue(false);
        jsonWriter.endArray();
        jsonWriter.close();
        assertEquals("[true,false]", stringWriter.toString());
    }

    public void testBoxedBooleans() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.startArray();
        jsonWriter.writeValue((Boolean) true);
        jsonWriter.writeValue((Boolean) false);
        jsonWriter.writeValue((Boolean) null);
        jsonWriter.endArray();
        jsonWriter.close();
        assertEquals("[true,false,null]", stringWriter.toString());
    }

    public void testNulls() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.startArray();
        jsonWriter.writeValue(null);
        jsonWriter.endArray();
        jsonWriter.close();
        assertEquals("[null]", stringWriter.toString());
    }

    public void testStrings() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.startArray();
        jsonWriter.writeValue("a");
        jsonWriter.writeValue("a\"");
        jsonWriter.writeValue("\"");
        jsonWriter.writeValue(":");
        jsonWriter.writeValue(",");
        jsonWriter.writeValue("\b");
        jsonWriter.writeValue("\f");
        jsonWriter.writeValue("\n");
        jsonWriter.writeValue("\r");
        jsonWriter.writeValue("\t");
        jsonWriter.writeValue(" ");
        jsonWriter.writeValue("\\");
        jsonWriter.writeValue("{");
        jsonWriter.writeValue("}");
        jsonWriter.writeValue("[");
        jsonWriter.writeValue("]");
        jsonWriter.writeValue("\0");
        jsonWriter.writeValue("\u0019");
        jsonWriter.endArray();
        jsonWriter.close();
        assertEquals("[\"a\","
                + "\"a\\\"\","
                + "\"\\\"\","
                + "\":\","
                + "\",\","
                + "\"\\b\","
                + "\"\\f\","
                + "\"\\n\","
                + "\"\\r\","
                + "\"\\t\","
                + "\" \","
                + "\"\\\\\","
                + "\"{\","
                + "\"}\","
                + "\"[\","
                + "\"]\","
                + "\"\\u0000\","
                + "\"\\u0019\"]", stringWriter.toString());
    }

    public void testUnicodeLineBreaksEscaped() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.startArray();
        jsonWriter.writeValue("\u2028 \u2029");
        jsonWriter.endArray();
        jsonWriter.close();
        assertEquals("[\"\\u2028 \\u2029\"]", stringWriter.toString());
    }

    public void testEmptyArray() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JSONWriter jsonWriter = new JSONWriter(stringWriter);
        jsonWriter.startArray();
        jsonWriter.endArray();
        jsonWriter.close();
        assertEquals("[]", stringWriter.toString());
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

    public void testDeepNestingArrays() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JSONWriter jsonWriter = new JSONWriter(stringWriter);
        for (int i = 0; i < 20; i++) {
            jsonWriter.startArray();
        }
        for (int i = 0; i < 20; i++) {
            jsonWriter.endArray();
        }
        jsonWriter.close();
        assertEquals("[[[[[[[[[[[[[[[[[[[[]]]]]]]]]]]]]]]]]]]]", stringWriter.toString());
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

    public void testStrictWriterDoesNotPermitMultipleTopLevelValues() throws IOException {
        StringWriter stringWriter = new StringWriter();
        JSONWriter writer = new JSONWriter(stringWriter);
        writer.startArray();
        writer.endArray();
        try {
            writer.startArray();
            fail();
        } catch (Exception expected) {
        }
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
        try {
            writer.close();
            fail();
        } catch (Exception e) {  }

    }
}
