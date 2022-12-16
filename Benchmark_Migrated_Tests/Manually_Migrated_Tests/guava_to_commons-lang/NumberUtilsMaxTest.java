package org.apache.commons.lang3.math;

import junit.framework.TestCase;

public class NumberUtilsMaxTest extends TestCase {
    private static final int LEAST = Integer.MIN_VALUE;
    private static final int GREATEST = Integer.MAX_VALUE;

    public void testMax_noArgs() {
        try {
            NumberUtils.max();
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public void testMax() {
        assertEquals(LEAST, NumberUtils.max(LEAST));
        assertEquals(GREATEST, NumberUtils.max(GREATEST));
        assertEquals((int) 9, NumberUtils.max((int) 8, (int) 6, (int) 7, (int) 5, (int) 3, (int) 0, (int) 9));
    }
}
