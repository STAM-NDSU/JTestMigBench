package com.google.gson;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GsonTest_toJsonTree {
    
    @Test
    public void toJSONArray() {
        final JsonElement jsonElement = new Gson().toJsonTree(null);
        assertTrue("toJSONArray() with null names should be JsonNull", jsonElement.isJsonNull());
    }

}