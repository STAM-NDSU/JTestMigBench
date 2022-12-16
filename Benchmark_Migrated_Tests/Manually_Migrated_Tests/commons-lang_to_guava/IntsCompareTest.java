package com.google.common.primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntsCompareTest {
    @Test
    public void compareInt() {
        assertTrue(Ints.compare(-3, 0) < 0);
        assertEquals(0, Ints.compare(113, 113));
        assertTrue(Ints.compare(213, 32) > 0);
    }
}
