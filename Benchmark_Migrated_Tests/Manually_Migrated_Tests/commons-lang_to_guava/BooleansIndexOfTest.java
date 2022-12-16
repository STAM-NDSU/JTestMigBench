package com.google.common.primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class BooleansIndexOfTest {
    @Test
    public void testIndexOfBoolean() {
        boolean[] array = null;
        try {
            Booleans.indexOf(array, true);
            fail();
        } catch (NullPointerException e) {}

        array = new boolean[0];
        assertEquals(-1, Booleans.indexOf(array, true));
        array = new boolean[]{true, false, true};
        assertEquals(0, Booleans.indexOf(array, true));
        assertEquals(1, Booleans.indexOf(array, false));
        array = new boolean[]{true, true};
        assertEquals(-1, Booleans.indexOf(array, false));
    }
}
