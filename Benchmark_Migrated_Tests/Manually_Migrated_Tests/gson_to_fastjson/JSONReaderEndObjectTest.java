package com.alibaba.fastjson;


import com.alibaba.fastjson.parser.JSONToken;
import junit.framework.TestCase;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class JSONReaderEndObjectTest extends TestCase {
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

    public void testCommentsInStringValue() throws Exception {
        JSONReader reader = new JSONReader(reader("[\"// comment\"]"));
        reader.startArray();
        assertEquals("// comment", reader.readString());
        reader.endArray();

        reader = new JSONReader(reader("{\"a\":\"#someComment\"}"));
        reader.startObject();
        assertEquals("a", reader.readString());
        assertEquals("#someComment", reader.readString());
        reader.endObject();

        reader = new JSONReader(reader("{\"#//a\":\"#some //Comment\"}"));
        reader.startObject();
        assertEquals("#//a", reader.readString());
        assertEquals("#some //Comment", reader.readString());
        reader.endObject();
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
