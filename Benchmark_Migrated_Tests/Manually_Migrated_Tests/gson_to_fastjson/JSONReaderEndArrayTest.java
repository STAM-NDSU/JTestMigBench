package com.alibaba.fastjson;

import com.alibaba.fastjson.parser.JSONToken;
import junit.framework.TestCase;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;


public class JSONReaderEndArrayTest extends TestCase {
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

    public void testLenientNonFiniteDoubles() throws IOException {
        String json = "[NaN, -Infinity, Infinity]";
        JSONReader reader = new JSONReader(reader(json));
        reader.startArray();
        assertTrue(Double.isNaN(reader.readObject(Double.TYPE)));
        assertEquals(Double.NEGATIVE_INFINITY, reader.readObject(Double.TYPE));
        assertEquals(Double.POSITIVE_INFINITY, reader.readObject(Double.TYPE));
        reader.endArray();
    }

    public void testLenientQuotedNonFiniteDoubles() throws IOException {
        String json = "[\"NaN\", \"-Infinity\", \"Infinity\"]";
        JSONReader reader = new JSONReader(reader(json));
        reader.startArray();
        assertTrue(Double.isNaN(reader.readObject(Double.TYPE)));
        assertEquals(Double.NEGATIVE_INFINITY, reader.readObject(Double.TYPE));
        assertEquals(Double.POSITIVE_INFINITY, reader.readObject(Double.TYPE));
        reader.endArray();
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

    public void disabled_testNumberWithOctalPrefix() throws IOException {
        String json = "[01]";
        JSONReader reader = new JSONReader(reader(json));
        reader.startArray();
        assertEquals("1", reader.readString());
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
        try {
            reader.readObject(Boolean.TYPE);
            fail();
        } catch (Exception expected) {
        }
        assertEquals("truey", reader.readString());
        reader.endArray();
    }

    public void disabled_testHighPrecisionLong() throws IOException {
        String json = "[9223372036854775806.000]";
        JSONReader reader = new JSONReader(reader(json));
        reader.startArray();
        assertEquals(9223372036854775806L, (long) reader.readLong());
        reader.endArray();
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

    public void testNextFailuresDoNotAdvance() throws IOException {
        JSONReader reader = new JSONReader(reader("{\"a\":true}"));
        reader.startObject();
        assertEquals("a", reader.readObject());
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
            reader.readObject();
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

    public void testIntegerMismatchFailuresDoNotAdvance() throws IOException {
        JSONReader reader = new JSONReader(reader("[1.5]"));
        reader.startArray();
        assertEquals(1.5d, reader.readObject(Double.TYPE));
        reader.endArray();
    }

    public void testCommentsInStringValue() throws Exception {
        JSONReader reader = new JSONReader(reader("[\"// comment\"]"));
        reader.startArray();
        assertEquals("// comment", reader.readString());
        reader.endArray();
    }

    public void testLenientUnnecessaryArraySeparators() throws IOException {
        JSONReader reader = new JSONReader(reader("[true,,true]"));
        reader.startArray();
        assertEquals(true, (boolean) reader.readObject(Boolean.TYPE));
        reader.readObject();
        assertEquals(true, (boolean) reader.readObject(Boolean.TYPE));
        reader.endArray();

        reader = new JSONReader(reader("[,true]"));
        reader.startArray();
        reader.readObject();
        assertEquals(true, (boolean) reader.readObject(Boolean.TYPE));
        reader.endArray();

        reader = new JSONReader(reader("[true,]"));
        reader.startArray();
        assertEquals(true, (boolean) reader.readObject(Boolean.TYPE));
        reader.readObject();
        reader.endArray();

        reader = new JSONReader(reader("[,]"));
        reader.startArray();
        reader.readObject();
        reader.readObject();
        reader.endArray();
    }

    public void testStrictMultipleTopLevelValues() throws IOException {
        JSONReader reader = new JSONReader(reader("[] []"));
        reader.startArray();
        reader.endArray();
        assertEquals(JSONToken.LBRACKET, reader.peek());
    }

    public void testLenientMultipleTopLevelValues() throws IOException {
        JSONReader reader = new JSONReader(reader("[] true {}"));
        reader.startArray();
        reader.endArray();
        assertEquals(true, (boolean) reader.readObject(Boolean.TYPE));
    }

    public void testStrictMultipleTopLevelValuesWithSkipValue() throws IOException {
        JSONReader reader = new JSONReader(reader("[] []"));
        reader.startArray();
        reader.endArray();
        try {
            reader.readObject();
            fail();
        } catch (Exception expected) {
        }
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

    public void testBomIgnoredAsFirstCharacterOfDocument() throws IOException {
        JSONReader reader = new JSONReader(reader("\ufeff[]"));
        reader.startArray();
        reader.endArray();
    }

    public void testBomForbiddenAsOtherCharacterInDocument() throws IOException {
        JSONReader reader = new JSONReader(reader("[\ufeff]"));
        reader.startArray();
        try {
            reader.endArray();
            fail();
        } catch (Exception expected) {
        }
    }

    public void testLenientVeryLongNumber() throws IOException {
        JSONReader reader = new JSONReader(reader("[0." + repeat('9', 8192) + "]"));
        reader.startArray();
        assertEquals(1d, reader.readObject(Double.TYPE));
        reader.endArray();
        assertEquals(JSONToken.EOF, reader.peek());
    }

    public void testVeryLongUnquotedLiteral() throws IOException {
        String literal = "a" + repeat('b', 81) + "c";
        JSONReader reader = new JSONReader(reader("[" + literal + "]"));
        reader.startArray();
        assertEquals(literal, reader.readString());
        reader.endArray();
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

    public void testVeryLongQuotedString() throws IOException {
        char[] stringChars = new char[1024 * 16];
        Arrays.fill(stringChars, 'x');
        String string = new String(stringChars);
        String json = "[\"" + string + "\"]";
        JSONReader reader = new JSONReader(reader(json));
        reader.startArray();
        assertEquals(string, reader.readString());
        reader.endArray();
    }

    public void testVeryLongUnquotedString() throws IOException {
        char[] stringChars = new char[1024 * 16];
        Arrays.fill(stringChars, 'x');
        String string = new String(stringChars);
        String json = "[" + string + "]";
        JSONReader reader = new JSONReader(reader(json));
        reader.startArray();
        assertEquals(string, reader.readString());
        reader.endArray();
    }

    public void testSkipVeryLongUnquotedString() throws IOException {
        JSONReader reader = new JSONReader(reader("[" + repeat('x', 8192) + "]"));
        reader.startArray();
        reader.readString();
        reader.endArray();
    }

    public void testSkipVeryLongQuotedString() throws IOException {
        JSONReader reader = new JSONReader(reader("[\"" + repeat('x', 8192) + "\"]"));
        reader.startArray();
        reader.readString();
        reader.endArray();
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
