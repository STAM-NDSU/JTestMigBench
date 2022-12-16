package org.apache.commons.lang3.math;

import junit.framework.TestCase;

public class NumberUtilsMinTest extends TestCase {
    private static final int LEAST = Integer.MIN_VALUE;
    private static final int GREATEST = Integer.MAX_VALUE;

    public void testMin_noArgs() {
        try {
            NumberUtils.min();
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    public void testMin() {
        assertEquals(LEAST, NumberUtils.min(LEAST));
        assertEquals(GREATEST, NumberUtils.min(GREATEST));
        assertEquals((int) 0, NumberUtils.min((int) 8, (int) 6, (int) 7, (int) 5, (int) 3, (int) 0, (int) 9));
    }
}
