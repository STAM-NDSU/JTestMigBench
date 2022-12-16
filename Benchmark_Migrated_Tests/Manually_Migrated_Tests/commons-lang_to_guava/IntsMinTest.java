package com.google.common.primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IntsMinTest {
    @Test
    public void testMinDouble() {
        assertEquals(-10, Ints.min(-10, -5, 0, 5, 10), 0.0001);
        assertEquals(-10, Ints.min(-5, 0, -10, 5, 10), 0.0001);
    }

    @Test
    public void testMinFloat() {
        assertEquals(-10, Ints.min(-10, -5, 0, 5, 10), 0.0001f);
        assertEquals(-10, Ints.min(-5, 0, -10, 5, 10), 0.0001f);
    }

    @Test
    public void testMinimumInt() {
        assertEquals(12345, Ints.min(12345, 12345 + 1, 12345 + 2), "minimum(int, int, int) 1 failed");
        assertEquals(12345, Ints.min(12345 + 1, 12345, 12345 + 2), "minimum(int, int, int) 2 failed");
        assertEquals(12345, Ints.min(12345 + 1, 12345 + 2, 12345), "minimum(int, int, int) 3 failed");
        assertEquals(12345, Ints.min(12345 + 1, 12345, 12345), "minimum(int, int, int) 4 failed");
        assertEquals(12345, Ints.min(12345, 12345, 12345), "minimum(int, int, int) 5 failed");
    }

    @Test
    public void testMinInt() {
        assertEquals(5, Ints.min(5), "min(int[]) failed for array length 1");
        assertEquals(6, Ints.min(6, 9), "min(int[]) failed for array length 2");

        assertEquals(-10, Ints.min(-10, -5, 0, 5, 10));
        assertEquals(-10, Ints.min(-5, 0, -10, 5, 10));
    }

    @Test
    public void testMinInt_emptyArray() {
        assertThrows(IllegalArgumentException.class, Ints::min);
    }

    @Test
    public void testMinInt_nullArray() {
        assertThrows(NullPointerException.class, () -> Ints.min((int[]) null));
    }

}
