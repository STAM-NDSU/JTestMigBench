package com.alibaba;

import com.alibaba.fastjson.JSONArray;
import org.junit.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class JSONArrayTest_remove {

    @Test
    public void remove() {
        String arrayStr1 =
                "["+
                        "1"+
                        "]";
        JSONArray jsonArray = JSONArray.parseArray(arrayStr1);
        jsonArray.remove(0);
        assertTrue("jsonArray should be empty", jsonArray.isEmpty());
        assertThrows(IndexOutOfBoundsException.class, () -> jsonArray.remove(5));
    }

}
