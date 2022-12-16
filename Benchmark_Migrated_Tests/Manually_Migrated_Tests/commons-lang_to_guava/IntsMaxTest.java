package com.google.common.primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IntsMaxTest {
    @Test
    public void testMaxDouble() {
        assertEquals(10, Ints.max(-10, -5, 0, 5, 10), 0.0001);
        assertEquals(10, Ints.max(-5, 0, 10, 5, -10), 0.0001);
    }

    @Test
    public void testMaxFloat() {
        assertEquals(10, Ints.max(-10, -5, 0, 5, 10), 0.0001f);
        assertEquals(10, Ints.max(-5, 0, 10, 5, -10), 0.0001f);
    }

    @Test
    public void testMaximumInt() {
        assertEquals(12345, Ints.max(12345, 12345 - 1, 12345 - 2), "maximum(int, int, int) 1 failed");
        assertEquals(12345, Ints.max(12345 - 1, 12345, 12345 - 2), "maximum(int, int, int) 2 failed");
        assertEquals(12345, Ints.max(12345 - 1, 12345 - 2, 12345), "maximum(int, int, int) 3 failed");
        assertEquals(12345, Ints.max(12345 - 1, 12345, 12345), "maximum(int, int, int) 4 failed");
        assertEquals(12345, Ints.max(12345, 12345, 12345), "maximum(int, int, int) 5 failed");
    }

    @Test
    public void testMaxInt() {
        assertEquals(5, Ints.max(5), "max(int[]) failed for array length 1");
        assertEquals(9, Ints.max(6, 9), "max(int[]) failed for array length 2");
        assertEquals(10, Ints.max(-10, -5, 0, 5, 10), "max(int[]) failed for array length 5");
        assertEquals(10, Ints.max(-10, -5, 0, 5, 10));
        assertEquals(10, Ints.max(-5, 0, 10, 5, -10));
    }

    @Test
    public void testMaxInt_emptyArray() {
        assertThrows(IllegalArgumentException.class, Ints::max);
    }


    @Test
    public void testMaxInt_nullArray() {
        assertThrows(NullPointerException.class, () -> Ints.max((int[]) null));
    }
}
