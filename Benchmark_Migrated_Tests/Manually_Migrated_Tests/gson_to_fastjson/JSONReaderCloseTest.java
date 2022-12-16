package com.alibaba.fastjson;

import com.alibaba.fastjson.parser.JSONToken;
import junit.framework.TestCase;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class JSONReaderCloseTest extends TestCase {
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
