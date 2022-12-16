package com.alibaba;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class JSONObjectTest_clear {

    @Test
    public void jsonObjectClearMethodTest() {
        //Adds random stuff to the JSONObject
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key1", 123);
        jsonObject.put("key2", "456");
        jsonObject.put("key3", new JSONObject());
        jsonObject.clear(); //Clears the JSONObject
        assertTrue("expected jsonObject.length() == 0", jsonObject.size() == 0); //Check if its length is 0
        assertFalse(jsonObject.containsKey("key1"));
    }

}
