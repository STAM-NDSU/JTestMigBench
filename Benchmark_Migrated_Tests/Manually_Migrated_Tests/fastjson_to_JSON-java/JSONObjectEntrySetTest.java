package org.json;

import junit.framework.TestCase;

public class JSONObjectEntrySetTest extends TestCase {
    public void test() throws Exception {
        JSONObject jsonObject = new JSONObject("{\"test\":null,\"a\":\"cc\"}");
        assertEquals(2, jsonObject.entrySet().size());
    }
}
