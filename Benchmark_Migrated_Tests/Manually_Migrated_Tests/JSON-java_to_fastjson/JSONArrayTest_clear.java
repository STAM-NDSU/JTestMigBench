package com.alibaba;

import com.alibaba.fastjson.JSONArray;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class JSONArrayTest_clear {

    @Test(expected = IndexOutOfBoundsException.class)
    public void jsonArrayClearMethodTest() {
        //Adds random stuff to the JSONArray
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(123);
        jsonArray.add("456");
        jsonArray.add(new JSONArray());
        jsonArray.clear(); //Clears the JSONArray
        assertTrue("expected jsonArray.length() == 0", jsonArray.size() == 0); //Check if its length is 0
        jsonArray.getIntValue(0); //Should throws org.json.JSONException: JSONArray[0] not found
    }

}
