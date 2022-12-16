package org.json.junit;

import junit.framework.TestCase;
import org.json.JSONObject;

import java.math.BigInteger;


public class JSONObjectGetBigIntegerTest extends TestCase {
    public void testParsingStringAsNumber() throws Exception {
        JSONObject json = new JSONObject("{'1':'1'}");

        assertFalse(json.get("1") instanceof Number);
        assertEquals(new BigInteger("1"), json.getBigInteger("1"));
    }
}
