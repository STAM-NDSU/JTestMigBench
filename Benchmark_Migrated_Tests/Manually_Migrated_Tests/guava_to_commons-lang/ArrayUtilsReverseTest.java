package org.apache.commons.lang3;

import junit.framework.TestCase;

import java.util.Arrays;

public class ArrayUtilsReverseTest extends TestCase {
    public void testReverse() {
        testReverse(new boolean[] {}, new boolean[] {});
        testReverse(new boolean[] {true}, new boolean[] {true});
        testReverse(new boolean[] {false, true}, new boolean[] {true, false});
        testReverse(new boolean[] {true, false, false}, new boolean[] {false, false, true});
        testReverse(new boolean[] {true, true, false, false}, new boolean[] {false, false, true, true});
    }

    private static void testReverse(boolean[] input, boolean[] expectedOutput) {
        input = Arrays.copyOf(input, input.length);
        ArrayUtils.reverse(input);
        assertTrue(Arrays.equals(expectedOutput, input));
    }

}
