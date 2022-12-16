package com.alibaba.fastjson;

import com.alibaba.fastjson.parser.JSONToken;
import junit.framework.TestCase;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;

public class JSONReaderPeekTest extends TestCase {
    public void testReadArray() throws IOException {
        JSONReader reader = new JSONReader(reader("[true, true]"));
        reader.startArray();
        assertEquals(true, (boolean) reader.readObject(Boolean.TYPE));
        assertEquals(true, (boolean) reader.readObject(Boolean.TYPE));
        reader.endArray();
        assertEquals(JSONToken.EOF, reader.peek());
    }

    public void testReadEmptyArray() throws IOException {
        JSONReader reader = new JSONReader(reader("[]"));
        reader.startArray();
        assertFalse(reader.hasNext());
        reader.endArray();
        assertEquals(JSONToken.EOF, reader.peek());
    }

    public void testReadObject() throws IOException {
        JSONReader reader = new JSONReader(reader(
                "{\"a\": \"android\", \"b\": \"banana\"}"));
        reader.startObject();
        assertEquals("a", reader.readString());
        assertEquals("android", reader.readString());
        assertEquals("b", reader.readString());
        assertEquals("banana", reader.readString());
        reader.endObject();
        assertEquals(JSONToken.EOF, reader.peek());
    }

    public void testReadEmptyObject() throws IOException {
        JSONReader reader = new JSONReader(reader("{}"));
        reader.startObject();
        assertFalse(reader.hasNext());
        reader.endObject();
        assertEquals(JSONToken.EOF, reader.peek());
    }

    public void testSkipArray() throws IOException {
        JSONReader reader = new JSONReader(reader(
                "{\"a\": [\"one\", \"two\", \"three\"], \"b\": 123}"));
        reader.startObject();
        assertEquals("a", reader.readString());
        reader.readObject();
        assertEquals("b", reader.readString());
        assertEquals(123, (int) reader.readInteger());
        reader.endObject();
        assertEquals(JSONToken.EOF, reader.peek());
    }

    public void testSkipArrayAfterPeek() throws Exception {
        JSONReader reader = new JSONReader(reader(
                "{\"a\": [\"one\", \"two\", \"three\"], \"b\": 123}"));
        reader.startObject();
        assertEquals("a", reader.readString());
        assertEquals(JSONToken.COLON, reader.peek());
        reader.readObject();
        assertEquals("b", reader.readString());
        assertEquals(123, (int) reader.readInteger());
        reader.endObject();
        assertEquals(JSONToken.EOF, reader.peek());
    }

    public void testSkipTopLevelObject() throws Exception {
        JSONReader reader = new JSONReader(reader(
                "{\"a\": [\"one\", \"two\", \"three\"], \"b\": 123}"));
        reader.readObject();
        assertEquals(JSONToken.EOF, reader.peek());
    }

    public void testSkipObject() throws IOException {
        JSONReader reader = new JSONReader(reader(
                "{\"a\": { \"c\": [], \"d\": [true, true, {}] }, \"b\": \"banana\"}"));
        reader.startObject();
        assertEquals("a", reader.readString());
        reader.readObject();
        assertEquals("b", reader.readString());
        reader.readObject();
        reader.endObject();
        assertEquals(JSONToken.EOF, reader.peek());
    }

    public void testSkipObjectAfterPeek() throws Exception {
        String json = "{" + "  \"one\": { \"num\": 1 }"
                + ", \"two\": { \"num\": 2 }" + ", \"three\": { \"num\": 3 }" + "}";
        JSONReader reader = new JSONReader(reader(json));
        reader.startObject();
        assertEquals("one", reader.readString());
        assertEquals(JSONToken.COLON, reader.peek());
        reader.readObject();
        assertEquals("two", reader.readString());
        assertEquals(JSONToken.COLON, reader.peek());
        reader.readObject();
        assertEquals("three", reader.readString());
        reader.readObject();
        reader.endObject();
        assertEquals(JSONToken.EOF, reader.peek());
    }

    public void testSkipInteger() throws IOException {
        JSONReader reader = new JSONReader(reader(
                "{\"a\":123456789,\"b\":-123456789}"));
        reader.startObject();
        assertEquals("a", reader.readString());
        reader.readObject();
        assertEquals("b", reader.readString());
        reader.readObject();
        reader.endObject();
        assertEquals(JSONToken.EOF, reader.peek());
    }

    public void testSkipDouble() throws IOException {
        JSONReader reader = new JSONReader(reader(
                "{\"a\":-123.456e-789,\"b\":123456789.0}"));
        reader.startObject();
        assertEquals("a", reader.readString());
        reader.readObject();
        assertEquals("b", reader.readString());
        reader.readObject();
        reader.endObject();
        assertEquals(JSONToken.EOF, reader.peek());
    }

    public void testHelloWorld() throws IOException {
        String json = "{\n" +
                "   \"hello\": true,\n" +
                "   \"foo\": [\"world\"]\n" +
                "}";
        JSONReader reader = new JSONReader(reader(json));
        reader.startObject();
        assertEquals("hello", reader.readString());
        assertEquals(true, (boolean) reader.readObject(Boolean.TYPE));
        assertEquals("foo", reader.readString());
        reader.startArray();
        assertEquals("world", reader.readString());
        reader.endArray();
        reader.endObject();
        assertEquals(JSONToken.EOF, reader.peek());
    }

    public void testCharacterUnescaping() throws IOException {
        String json = "[\"a\","
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
                + "\"\\u0019\","
                + "\"\\u20AC\""
                + "]";
        JSONReader reader = new JSONReader(reader(json));
        reader.startArray();
        assertEquals("a", reader.readString());
        assertEquals("a\"", reader.readString());
        assertEquals("\"", reader.readString());
        assertEquals(":", reader.readString());
        assertEquals(",", reader.readString());
        assertEquals("\b", reader.readString());
        assertEquals("\f", reader.readString());
        assertEquals("\n", reader.readString());
        assertEquals("\r", reader.readString());
        assertEquals("\t", reader.readString());
        assertEquals(" ", reader.readString());
        assertEquals("\\", reader.readString());
        assertEquals("{", reader.readString());
        assertEquals("}", reader.readString());
        assertEquals("[", reader.readString());
        assertEquals("]", reader.readString());
        assertEquals("\0", reader.readString());
        assertEquals("\u0019", reader.readString());
        assertEquals("\u20AC", reader.readString());
        reader.endArray();
        assertEquals(JSONToken.EOF, reader.peek());
    }

    public void testDoubles() throws IOException {
        String json = "[-0.0,"
                + "1.0,"
                + "1.7976931348623157E308,"
                + "4.9E-324,"
                + "0.0,"
                + "-0.5,"
                + "2.2250738585072014E-308,"
                + "3.141592653589793,"
                + "2.718281828459045]";
        JSONReader reader = new JSONReader(reader(json));
        reader.startArray();
        assertEquals(-0.0, reader.readObject(Double.TYPE));
        assertEquals(1.0, reader.readObject(Double.TYPE));
        assertEquals(1.7976931348623157E308, reader.readObject(Double.TYPE));
        assertEquals(4.9E-324, reader.readObject(Double.TYPE));
        assertEquals(0.0, reader.readObject(Double.TYPE));
        assertEquals(-0.5, reader.readObject(Double.TYPE));
        assertEquals(2.2250738585072014E-308, reader.readObject(Double.TYPE));
        assertEquals(3.141592653589793, reader.readObject(Double.TYPE));
        assertEquals(2.718281828459045, reader.readObject(Double.TYPE));
        reader.endArray();
        assertEquals(JSONToken.EOF, reader.peek());
    }

    public void testLongs() throws IOException {
        String json = "[0,0,0,"
                + "1,1,1,"
                + "-1,-1,-1,"
                + "-9223372036854775808,"
                + "9223372036854775807]";
        JSONReader reader = new JSONReader(reader(json));
        reader.startArray();
        assertEquals(0L, (long) reader.readLong());
        assertEquals(0, (int) reader.readInteger());
        assertEquals(0.0, reader.readObject(Double.TYPE));
        assertEquals(1L, (long) reader.readLong());
        assertEquals(1, (int) reader.readInteger());
        assertEquals(1.0, reader.readObject(Double.TYPE));
        assertEquals(-1L, (long) reader.readLong());
        assertEquals(-1, (int) reader.readInteger());
        assertEquals(-1.0, reader.readObject(Double.TYPE));
        assertEquals(Long.MIN_VALUE, (long) reader.readLong());
        assertEquals(Long.MAX_VALUE, (long) reader.readLong());
        reader.endArray();
        assertEquals(JSONToken.EOF, reader.peek());
    }

    public void testBooleans() throws IOException {
        JSONReader reader = new JSONReader(reader("[true,false]"));
        reader.startArray();
        assertEquals(true, (boolean) reader.readObject(Boolean.TYPE));
        assertEquals(false, (boolean) reader.readObject(Boolean.TYPE));
        reader.endArray();
        assertEquals(JSONToken.EOF, reader.peek());
    }

    public void testPeekingUnquotedStringsPrefixedWithBooleans() throws IOException {
        JSONReader reader = new JSONReader(reader("[truey]"));
        reader.startArray();
        assertEquals(JSONToken.LBRACKET, reader.peek());
        try {
            reader.readObject(Boolean.TYPE);
            fail();
        } catch (IllegalStateException expected) {
        }
        assertEquals("truey", reader.readString());
        reader.endArray();
    }

    public void testPeekingUnquotedStringsPrefixedWithIntegers() throws IOException {
        JSONReader reader = new JSONReader(reader("[12.34e5x]"));
        reader.startArray();
        assertEquals(JSONToken.LITERAL_FLOAT, reader.peek());
        try {
            reader.readInteger();
            fail();
        } catch (Exception expected) {
        }
        assertEquals("12.34e5x", reader.readString());
    }

    public void testPeekLongMinValue() throws IOException {
        JSONReader reader = new JSONReader(reader("[-9223372036854775808]"));
        reader.startArray();
        assertEquals(JSONToken.LITERAL_INT, reader.peek());
        assertEquals(-9223372036854775808L, (long) reader.readLong());
    }

    public void testPeekLongMaxValue() throws IOException {
        JSONReader reader = new JSONReader(reader("[9223372036854775807]"));
        reader.startArray();
        assertEquals(JSONToken.LITERAL_INT, reader.peek());
        assertEquals(9223372036854775807L, (long) reader.readLong());
    }

    public void testLongLargerThanMaxLongThatWrapsAround() throws IOException {
        JSONReader reader = new JSONReader(reader("[22233720368547758070]"));
        reader.startArray();
        assertEquals(JSONToken.LITERAL_INT, reader.peek());
        try {
            reader.readLong();
            fail();
        } catch (Exception expected) {
        }
    }

    public void testLongLargerThanMinLongThatWrapsAround() throws IOException {
        JSONReader reader = new JSONReader(reader("[-22233720368547758070]"));
        reader.startArray();
        assertEquals(JSONToken.LITERAL_INT, reader.peek());
        try {
            reader.readLong();
            fail();
        } catch (Exception expected) {
        }
    }

    public void testNegativeZero() throws Exception {
        JSONReader reader = new JSONReader(reader("[\"-0\"]"));
        reader.startArray();
        assertEquals(JSONToken.LITERAL_STRING, reader.peek());
        assertEquals("-0", reader.readString());
    }

    public void testPeekMuchLargerThanLongMinValue() throws IOException {
        JSONReader reader = new JSONReader(reader("[-92233720368547758080]"));
        reader.startArray();
        assertEquals(JSONToken.LITERAL_INT, reader.peek());
        try {
            reader.readLong();
            fail();
        } catch (Exception expected) {
        }
        assertEquals(-92233720368547758080d, reader.readObject(Double.TYPE));
    }

    public void testQuotedNumberWithEscape() throws IOException {
        JSONReader reader = new JSONReader(reader("[\"12\u00334\"]"));
        reader.startArray();
        assertEquals(JSONToken.LITERAL_STRING, reader.peek());
        assertEquals(1234, (int) reader.readInteger());
    }

    public void testMixedCaseLiterals() throws IOException {
        JSONReader reader = new JSONReader(reader("[True,TruE,False,FALSE,NULL,nulL]"));
        reader.startArray();
        assertEquals(true, (boolean) reader.readObject(Boolean.TYPE));
        assertEquals(true, (boolean) reader.readObject(Boolean.TYPE));
        assertEquals(false, (boolean) reader.readObject(Boolean.TYPE));
        assertEquals(false, (boolean) reader.readObject(Boolean.TYPE));
        reader.readObject();
        reader.readObject();
        reader.endArray();
        assertEquals(JSONToken.EOF, reader.peek());
    }

    public void testPrematurelyClosed() throws IOException {
        try {
            JSONReader reader = new JSONReader(reader("{\"a\":[]}"));
            reader.startObject();
            reader.close();
            reader.readString();
            fail();
        } catch (Exception expected) {
        }

        try {
            JSONReader reader = new JSONReader(reader("{\"a\":[]}"));
            reader.close();
            reader.startObject();
            fail();
        } catch (Exception expected) {
        }

        try {
            JSONReader reader = new JSONReader(reader("{\"a\":true}"));
            reader.startObject();
            reader.readString();
            reader.peek();
            reader.close();
            reader.readObject(Boolean.TYPE);
            fail();
        } catch (Exception expected) {
        }
    }

    public void testNextFailuresDoNotAdvance() throws IOException {
        JSONReader reader = new JSONReader(reader("{\"a\":true}"));
        reader.startObject();
        assertEquals("a", reader.readString());
        try {
            reader.startArray();
            fail();
        } catch (Exception expected) {
        }
        try {
            reader.endArray();
            fail();
        } catch (Exception expected) {
        }
        try {
            reader.startObject();
            fail();
        } catch (Exception expected) {
        }
        try {
            reader.endObject();
            fail();
        } catch (Exception expected) {
        }
        assertEquals(true, (boolean) reader.readObject(Boolean.TYPE));
        try {
            reader.readString();
            fail();
        } catch (Exception expected) {
        }
        try {
            reader.startArray();
            fail();
        } catch (Exception expected) {
        }
        try {
            reader.endArray();
            fail();
        } catch (Exception expected) {
        }
        reader.endObject();
        assertEquals(JSONToken.EOF, reader.peek());
        reader.close();
    }

    public void testStrictMultipleTopLevelValues() throws IOException {
        JSONReader reader = new JSONReader(reader("[] []"));
        reader.startArray();
        reader.endArray();
        try {
            reader.peek();
            fail();
        } catch (Exception expected) {
        }
    }

    public void testLenientMultipleTopLevelValues() throws IOException {
        JSONReader reader = new JSONReader(reader("[] true {}"));
        reader.startArray();
        reader.endArray();
        assertEquals(true, (boolean) reader.readObject(Boolean.TYPE));
        reader.startObject();
        reader.endObject();
        assertEquals(JSONToken.EOF, reader.peek());
    }

    public void testTopLevelValueTypes() throws IOException {
        JSONReader reader1 = new JSONReader(reader("true"));
        assertTrue(reader1.readObject(Boolean.TYPE));
        assertEquals(JSONToken.EOF, reader1.peek());

        JSONReader reader2 = new JSONReader(reader("false"));
        assertFalse(reader2.readObject(Boolean.TYPE));
        assertEquals(JSONToken.EOF, reader2.peek());

        JSONReader reader3 = new JSONReader(reader("null"));
        assertEquals(JSONToken.NULL, reader3.peek());
        reader3.readObject();
        assertEquals(JSONToken.EOF, reader3.peek());

        JSONReader reader4 = new JSONReader(reader("123"));
        assertEquals(123, (int) reader4.readInteger());
        assertEquals(JSONToken.EOF, reader4.peek());

        JSONReader reader5 = new JSONReader(reader("123.4"));
        assertEquals(123.4, reader5.readObject(Double.TYPE));
        assertEquals(JSONToken.EOF, reader5.peek());

        JSONReader reader6 = new JSONReader(reader("\"a\""));
        assertEquals("a", reader6.readString());
        assertEquals(JSONToken.EOF, reader6.peek());
    }

    public void testTopLevelValueTypeWithSkipValue() throws IOException {
        JSONReader reader = new JSONReader(reader("true"));
        reader.readObject();
        assertEquals(JSONToken.EOF, reader.peek());
    }

    public void testLenientNonExecutePrefix() throws IOException {
        JSONReader reader = new JSONReader(reader(")]}'\n []"));
        reader.startArray();
        reader.endArray();
        assertEquals(JSONToken.EOF, reader.peek());
    }

    public void testLenientNonExecutePrefixWithLeadingWhitespace() throws IOException {
        JSONReader reader = new JSONReader(reader("\r\n \t)]}'\n []"));
        reader.startArray();
        reader.endArray();
        assertEquals(JSONToken.EOF, reader.peek());
    }

    public void testFailWithPositionDeepPath() throws IOException {
        JSONReader reader = new JSONReader(reader("[1,{\"a\":[2,3,}"));
        reader.startArray();
        reader.readInteger();
        reader.startObject();
        reader.readString();
        reader.startArray();
        reader.readInteger();
        reader.readInteger();
        try {
            reader.peek();
            fail();
        } catch (Exception expected) {
        }
    }

    public void testLenientVeryLongNumber() throws IOException {
        JSONReader reader = new JSONReader(reader("[0." + repeat('9', 8192) + "]"));
        reader.startArray();
        assertEquals(JSONToken.LITERAL_FLOAT, reader.peek());
        assertEquals(1d, reader.readObject(Double.TYPE));
        reader.endArray();
        assertEquals(JSONToken.EOF, reader.peek());
    }

    public void testDeeplyNestedArrays() throws IOException {
        // this is nested 40 levels deep; Gson is tuned for nesting is 30 levels deep or fewer
        JSONReader reader = new JSONReader(reader(
                "[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]"));
        for (int i = 0; i < 40; i++) {
            reader.startArray();
        }
        for (int i = 0; i < 40; i++) {
            reader.endArray();
        }
        assertEquals(JSONToken.EOF, reader.peek());
    }

    public void testDeeplyNestedObjects() throws IOException {
        // Build a JSON document structured like {"a":{"a":{"a":{"a":true}}}}, but 40 levels deep
        String array = "{\"a\":%s}";
        String json = "true";
        for (int i = 0; i < 40; i++) {
            json = String.format(array, json);
        }

        JSONReader reader = new JSONReader(reader(json));
        for (int i = 0; i < 40; i++) {
            reader.startObject();
            assertEquals("a", reader.readString());
        }
        assertEquals(true, (boolean) reader.readObject(Boolean.TYPE));
        for (int i = 0; i < 40; i++) {
            reader.endObject();
        }
        assertEquals(JSONToken.EOF, reader.peek());
    }

    public void testStringEndingInSlash() throws IOException {
        JSONReader reader = new JSONReader(reader("/"));
        try {
            reader.peek();
            fail();
        } catch (Exception expected) {
        }
    }

    public void testDocumentWithCommentEndingInSlash() throws IOException {
        JSONReader reader = new JSONReader(reader("/* foo *//"));
        try {
            reader.peek();
            fail();
        } catch (Exception expected) {
        }
    }

    public void testStringWithLeadingSlash() throws IOException {
        JSONReader reader = new JSONReader(reader("/x"));
        try {
            reader.peek();
            fail();
        } catch (Exception expected) {
        }
    }

    public void testUnterminatedObject() throws IOException {
        JSONReader reader = new JSONReader(reader("{\"a\":\"android\"x"));
        reader.startObject();
        assertEquals("a", reader.readString());
        assertEquals("android", reader.readString());
        try {
            reader.peek();
            fail();
        } catch (Exception expected) {
        }
    }

    public void testVeryLongUnterminatedString() throws IOException {
        char[] stringChars = new char[1024 * 16];
        Arrays.fill(stringChars, 'x');
        String string = new String(stringChars);
        String json = "[" + string;
        JSONReader reader = new JSONReader(reader(json));
        reader.startArray();
        assertEquals(string, reader.readString());
        try {
            reader.peek();
            fail();
        } catch (Exception expected) {
        }
    }

    public void testSkipTopLevelUnquotedString() throws IOException {
        JSONReader reader = new JSONReader(reader(repeat('x', 8192)));
        reader.readObject();
        assertEquals(JSONToken.EOF, reader.peek());
    }

    public void testSkipTopLevelQuotedString() throws IOException {
        JSONReader reader = new JSONReader(reader("\"" + repeat('x', 8192) + "\""));
        reader.readObject();
        assertEquals(JSONToken.EOF, reader.peek());
    }

    public void testStringAsNumberWithTruncatedExponent() throws IOException {
        JSONReader reader = new JSONReader(reader("[123e]"));
        reader.startArray();
        assertEquals(JSONToken.LITERAL_FLOAT, reader.peek());
    }

    public void testStringAsNumberWithDigitAndNonDigitExponent() throws IOException {
        JSONReader reader = new JSONReader(reader("[123e4b]"));
        reader.startArray();
        assertEquals(JSONToken.LITERAL_FLOAT, reader.peek());
    }

    public void testStringAsNumberWithNonDigitExponent() throws IOException {
        JSONReader reader = new JSONReader(reader("[123eb]"));
        reader.startArray();
        assertEquals(JSONToken.LITERAL_FLOAT, reader.peek());
    }

    public void testEmptyStringName() throws IOException {
        JSONReader reader = new JSONReader(reader("{\"\":true}"));
        assertEquals(JSONToken.LBRACE, reader.peek());
        reader.startObject();
        assertEquals(JSONToken.LITERAL_STRING, reader.peek());
        assertEquals("", reader.readString());
        assertEquals(JSONToken.COLON, reader.peek());
        assertEquals(true, (boolean) reader.readObject(Boolean.TYPE));
        assertEquals(JSONToken.RBRACE, reader.peek());
        reader.endObject();
        assertEquals(JSONToken.EOF, reader.peek());
    }

    public void testStrictExtraCommasInMaps() throws IOException {
        JSONReader reader = new JSONReader(reader("{\"a\":\"b\",}"));
        reader.startObject();
        assertEquals("a", reader.readString());
        assertEquals("b", reader.readString());
        assertEquals(JSONToken.COMMA, reader.peek());
    }

    public void testLenientExtraCommasInMaps() throws IOException {
        JSONReader reader = new JSONReader(reader("{\"a\":\"b\",}"));
        reader.startObject();
        assertEquals("a", reader.readString());
        assertEquals("b", reader.readString());
        assertEquals(JSONToken.COMMA, reader.peek());
    }

    public void testUnterminatedStringFailure() throws IOException {
        JSONReader reader = new JSONReader(reader("[\"string"));
        reader.startArray();
        assertEquals(JSONToken.LITERAL_STRING, reader.peek());
        try {
            reader.readString();
            fail();
        } catch (Exception expected) {
        }
    }

    private String repeat(char c, int count) {
        char[] array = new char[count];
        Arrays.fill(array, c);
        return new String(array);
    }

    private Reader reader(final String s) {
        /* if (true) */ return new StringReader(s);
    /* return new Reader() {
      int position = 0;
      @Override public int read(char[] buffer, int offset, int count) throws IOException {
        if (position == s.length()) {
          return -1;
        } else if (count > 0) {
          buffer[offset] = s.charAt(position++);
          return 1;
        } else {
          throw new IllegalArgumentException();
        }
      }
      @Override public void close() throws IOException {
      }
    }; */
    }
}
