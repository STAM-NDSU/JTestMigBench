package org.apache.commons.lang3;

import junit.framework.TestCase;

public class ArrayUtilsLastIndexOfTest extends TestCase {
    private static final boolean[] EMPTY = {};
    private static final boolean[] ARRAY_FALSE = {false};
    private static final boolean[] ARRAY_FALSE_FALSE = {false, false};
    private static final boolean[] ARRAY_FALSE_TRUE = {false, true};


    public void testLastIndexOf() {
        assertEquals(-1, ArrayUtils.lastIndexOf(EMPTY, false));
        assertEquals(-1, ArrayUtils.lastIndexOf(ARRAY_FALSE, true));
        assertEquals(-1, ArrayUtils.lastIndexOf(ARRAY_FALSE_FALSE, true));
        assertEquals(0, ArrayUtils.lastIndexOf(ARRAY_FALSE, false));
        assertEquals(0, ArrayUtils.lastIndexOf(ARRAY_FALSE_TRUE, false));
        assertEquals(1, ArrayUtils.lastIndexOf(ARRAY_FALSE_TRUE, true));
        assertEquals(2, ArrayUtils.lastIndexOf(new boolean[] {false, true, true}, true));
    }
}
