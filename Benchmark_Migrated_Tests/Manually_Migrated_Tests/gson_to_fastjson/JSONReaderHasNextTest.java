package com.alibaba.fastjson;

import com.alibaba.fastjson.parser.JSONToken;
import junit.framework.TestCase;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class JSONReaderHasNextTest extends TestCase {
    public void testReadEmptyArray() throws IOException {
        JSONReader reader = new JSONReader(reader("[]"));
        reader.startArray();
        assertFalse(reader.hasNext());
        reader.endArray();
        assertEquals(JSONToken.EOF, reader.peek());
    }

    public void testReadEmptyObject() throws IOException {
        JSONReader reader = new JSONReader(reader("{}"));
        reader.startObject();
        assertFalse(reader.hasNext());
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
