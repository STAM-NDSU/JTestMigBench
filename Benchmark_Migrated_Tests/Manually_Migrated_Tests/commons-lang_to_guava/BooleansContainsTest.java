package com.google.common.primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BooleansContainsTest {
    @Test
    public void testContainsBoolean() {
        boolean[] array = null;
        try {
            Booleans.contains(array, true);
            fail();
        } catch (NullPointerException e) {  }

        array = new boolean[]{true, false, true};
        assertTrue(Booleans.contains(array, true));
        assertTrue(Booleans.contains(array, false));
        array = new boolean[]{true, true};
        assertTrue(Booleans.contains(array, true));
        assertFalse(Booleans.contains(array, false));
    }
}
