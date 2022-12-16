package com.google.common.base;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlatformSystemNanoTimeTest {
    /**
     * Tests whether the current time is correctly determined.
     */
    @Test
    public void testNow() {
        final long nowNanos = Platform.systemNanoTime();
        final long deltaNanos = Math.abs(System.nanoTime() - nowNanos);
        assertTrue(deltaNanos < 100_000, String.format("Delta %,d ns to current time too large", deltaNanos));
    }
}
