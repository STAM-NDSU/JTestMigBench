package com.alibaba.fastjson;

import junit.framework.TestCase;

import java.io.StringReader;

public class JSONReaderHasNextTest2 extends TestCase {
    private JSONReader parser;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        parser = new JSONReader(new StringReader("['one','two']"));
    }

    public void testIterator() {
        parser.startArray();
        assertTrue(parser.hasNext());
        assertEquals("one", parser.readString());
        assertTrue(parser.hasNext());
        assertEquals("two", parser.readString());
        assertFalse(parser.hasNext());
    }

    public void testNoSideEffectForHasNext() throws Exception {
        parser.startArray();
        assertTrue(parser.hasNext());
        assertTrue(parser.hasNext());
        assertTrue(parser.hasNext());
        assertEquals("one", parser.readString());

        assertTrue(parser.hasNext());
        assertTrue(parser.hasNext());
        assertEquals("two", parser.readString());

        assertFalse(parser.hasNext());
        assertFalse(parser.hasNext());
    }
}
