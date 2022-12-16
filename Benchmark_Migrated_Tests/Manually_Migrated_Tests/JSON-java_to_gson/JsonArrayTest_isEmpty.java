package com.google.gson;

import org.junit.Test;

import static org.junit.Assert.*;

public class JsonArrayTest_isEmpty {
    
    @Test
    public void emptyJsonObject() {
        JsonArray jsonObject = new JsonArray();
        assertTrue("jsonObject should be empty", jsonObject.isEmpty());
    }

}