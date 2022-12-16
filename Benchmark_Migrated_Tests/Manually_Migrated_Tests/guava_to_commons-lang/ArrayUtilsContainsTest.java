package org.apache.commons.lang3;

import junit.framework.TestCase;

public class ArrayUtilsContainsTest extends TestCase {
    private static final boolean[] EMPTY = {};
    private static final boolean[] ARRAY_FALSE = {false};
    private static final boolean[] ARRAY_FALSE_TRUE = {false, true};

    public void testContains() {
        assertFalse(ArrayUtils.contains(EMPTY, false));
        assertFalse(ArrayUtils.contains(ARRAY_FALSE, true));
        assertTrue(ArrayUtils.contains(ARRAY_FALSE, false));
        assertTrue(ArrayUtils.contains(ARRAY_FALSE_TRUE, false));
        assertTrue(ArrayUtils.contains(ARRAY_FALSE_TRUE, true));
    }
}
