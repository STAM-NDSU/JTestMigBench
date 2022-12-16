package org.apache.commons.lang3;

import junit.framework.TestCase;

public class ArrayUtilsIndexOfTest extends TestCase {
    private static final boolean[] EMPTY = {};
    private static final boolean[] ARRAY_FALSE = {false};
    private static final boolean[] ARRAY_FALSE_FALSE = {false, false};
    private static final boolean[] ARRAY_FALSE_TRUE = {false, true};

    public void testIndexOf_arrays() {
        assertEquals(-1, ArrayUtils.indexOf(EMPTY, false));
        assertEquals(-1, ArrayUtils.indexOf(ARRAY_FALSE, true));
        assertEquals(-1, ArrayUtils.indexOf(ARRAY_FALSE_FALSE, true));
        assertEquals(0, ArrayUtils.indexOf(ARRAY_FALSE, false));
        assertEquals(0, ArrayUtils.indexOf(ARRAY_FALSE_TRUE, false));
        assertEquals(1, ArrayUtils.indexOf(ARRAY_FALSE_TRUE, true));
        assertEquals(2, ArrayUtils.indexOf(new boolean[] {false, false, true}, true));
    }

}
