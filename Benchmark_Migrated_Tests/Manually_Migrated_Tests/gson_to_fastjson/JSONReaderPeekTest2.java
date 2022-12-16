package com.alibaba.fastjson;

import com.alibaba.fastjson.parser.JSONToken;
import junit.framework.TestCase;

import java.io.IOException;
import java.io.StringReader;

public class JSONReaderPeekTest2 extends TestCase {
    public void testSkipValue_emptyJsonObject() throws IOException {
        JSONReader in = new JSONReader(new StringReader(""));
        assertEquals(JSONToken.EOF, in.peek());
    }

    public void testSkipValue_filledJsonObject() throws IOException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.add('c');
        jsonArray.add("text");
        jsonObject.put("a", jsonArray);
        jsonObject.put("b", true);
        jsonObject.put("i", 1);
        jsonObject.put("n", null);
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("n", 2L);
        jsonObject.put("o", jsonObject2);
        jsonObject.put("s", "text");
        JSONReader in = new JSONReader(new StringReader(jsonObject.toString()));
        in.readObject();
        assertEquals(JSONToken.EOF, in.peek());
    }
}
