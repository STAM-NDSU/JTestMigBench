package com.alibaba;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.JSONScanner;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.junit.Assert.*;

public class JSONScannerTest_next {

//    @Test
//    public void testNextBackComboWithNewLines() {
//        final String testString = "this is\nA test\r\nWith some different\rNew Lines";
//        //                         ^       ^         ^                    ^
//        // index positions         0       8        16                   36
//        final JSONScanner tokener = new JSONScanner(testString);
//        assertEquals(" at 0 [character 1 line 1]", tokener.toString());
//        assertEquals('t',tokener.next());
//        assertEquals(" at 1 [character 2 line 1]", tokener.toString());
//        tokener.skipWhitespace();
//        tokener.skipTo('\n');
//        assertEquals("skipTo() improperly modifying indexes"," at 7 [character 8 line 1]", tokener.toString());
//        assertEquals('\n',tokener.next());
//        assertEquals(" at 8 [character 0 line 2]", tokener.toString());
//        assertEquals('A',tokener.next());
//        assertEquals(" at 9 [character 1 line 2]", tokener.toString());
//        tokener.back();
//        assertEquals(" at 8 [character 0 line 2]", tokener.toString());
//        tokener.skipTo('\r');
//        assertEquals("skipTo() improperly modifying indexes"," at 14 [character 6 line 2]", tokener.toString());
//        // verify \r\n combo doesn't increment the line twice
//        assertEquals('\r', tokener.next());
//        assertEquals(" at 15 [character 0 line 3]", tokener.toString());
//        assertEquals('\n', tokener.next());
//        assertEquals(" at 16 [character 0 line 3]", tokener.toString());
//        // verify stepping back after reading the \n of an \r\n combo doesn't  increment the line incorrectly
//        tokener.back();
//        assertEquals(" at 15 [character 6 line 2]", tokener.toString());
//        assertEquals('\n', tokener.next());
//        assertEquals(" at 16 [character 0 line 3]", tokener.toString());
//        assertEquals('W', tokener.next());
//        assertEquals(" at 17 [character 1 line 3]", tokener.toString());
//        assertEquals('i', tokener.next());
//        assertEquals(" at 18 [character 2 line 3]", tokener.toString());
//        tokener.skipTo('\r');
//        assertEquals("skipTo() improperly modifying indexes"," at 35 [character 19 line 3]", tokener.toString());
//        assertEquals('\r', tokener.next());
//        assertEquals(" at 36 [character 0 line 4]", tokener.toString());
//        tokener.back();
//        assertEquals(" at 35 [character 19 line 3]", tokener.toString());
//        assertEquals('\r', tokener.next());
//        assertEquals(" at 36 [character 0 line 4]", tokener.toString());
//        assertEquals('N', tokener.next());
//        assertEquals(" at 37 [character 1 line 4]", tokener.toString());
//
//        // verify we get the same data just walking though, no calls to back
//        final JSONScanner t2 = new JSONScanner(testString);
//        for(int i=0; i<7; i++) {
//            assertTrue(t2.toString().startsWith(" at " + i + " "));
//            assertEquals(testString.charAt(i), t2.next());
//        }
//        assertEquals(" at 7 [character 8 line 1]", t2.toString());
//        assertEquals(testString.charAt(7), t2.next());
//        assertEquals(" at 8 [character 0 line 2]", t2.toString());
//        for(int i=8; i<14; i++) {
//            assertTrue(t2.toString().startsWith(" at " + i + " "));
//            assertEquals(testString.charAt(i), t2.next());
//        }
//        assertEquals(" at 14 [character 6 line 2]", t2.toString());
//        assertEquals('\r', t2.next());
//        assertEquals(" at 15 [character 0 line 3]", t2.toString());
//        assertEquals('\n', t2.next());
//        assertEquals(" at 16 [character 0 line 3]", t2.toString());
//        assertEquals('W', t2.next());
//        assertEquals(" at 17 [character 1 line 3]", t2.toString());
//        for(int i=17; i<37; i++) {
//            assertTrue(t2.toString().startsWith(" at " + i + " "));
//            assertEquals(testString.charAt(i), t2.next());
//        }
//        assertEquals(" at 37 [character 1 line 4]", t2.toString());
//        for(int i=37; i<testString.length(); i++) {
//            assertTrue(t2.toString().startsWith(" at " + i + " "));
//            assertEquals(testString.charAt(i), t2.next());
//        }
//        assertEquals(" at "+ testString.length() +" [character 9 line 4]", t2.toString());
//        // end of the input
//        assertEquals(0, t2.next());
//        assertFalse(t2.more());
//    }
//
//    @Test
//    public void verifyBackFailureDoubleBack() throws IOException {
//        try {
//            final JSONScanner tokener = new JSONScanner("some test string");
//            tokener.next();
//            tokener.back();
//            try {
//                // this should fail since the index is 0;
//                tokener.back();
//                fail("Expected an exception");
//            } catch (JSONException e) {
//                assertEquals("Stepping back two steps is not supported", e.getMessage());
//            } catch (Exception e) {
//                fail("Unknown Exception type " + e.getClass().getCanonicalName()+" with message "+e.getMessage());
//            }
//        } finally {
//            reader.close();
//        }
//    }



}
