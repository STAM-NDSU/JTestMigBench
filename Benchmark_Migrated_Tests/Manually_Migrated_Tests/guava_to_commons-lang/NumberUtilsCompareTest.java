package org.apache.commons.lang3.math;

import junit.framework.TestCase;

public class NumberUtilsCompareTest extends TestCase {
    private static final int LEAST = Integer.MIN_VALUE;
    private static final int GREATEST = Integer.MAX_VALUE;

    private static final int[] VALUES = {LEAST, (int) -1, (int) 0, (int) 1, GREATEST};

    public void testCompare() {
        for (int x : VALUES) {
            for (int y : VALUES) {
                // note: spec requires only that the sign is the same
                assertEquals(x + ", " + y, Integer.valueOf(x).compareTo(y), NumberUtils.compare(x, y));
            }
        }
    }
}
