package com.alibaba;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JSONObjectTest_isEmpty {

    @Test
    public void emptyJsonObject() {
        JSONObject jsonObject = new JSONObject();
        assertTrue("jsonObject should be empty", jsonObject.isEmpty());
    }

    @Test
    public void jsonObjectputNull() {
        // put null should remove the item.
        String str = "{\"myKey\": \"myval\"}";
        JSONObject jsonObjectRemove = JSONObject.parseObject(str);
        jsonObjectRemove.remove("myKey");
        assertTrue("jsonObject should be empty", jsonObjectRemove.isEmpty());

        JSONObject jsonObjectPutNull = JSONObject.parseObject(str);
        jsonObjectPutNull.put("myKey", null);
        assertFalse("jsonObject should not be empty", jsonObjectPutNull.isEmpty());
    }

}
