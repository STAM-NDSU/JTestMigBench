package org.apache.commons.lang3;

import junit.framework.TestCase;

import java.util.Arrays;

import static com.google.common.truth.Truth.assertThat;

public class ArrayUtilsIsEmptyTest extends TestCase {
    public void testIsEmpty() {
        assertThat(ArrayUtils.isEmpty((int[]) null)).isTrue();
        assertThat(ArrayUtils.isEmpty(new int[] {0})).isFalse();
        assertThat(ArrayUtils.isEmpty(new int[] {0,1,3})).isFalse();

        int [] array = new int[] {0,1,3};
        assertThat(ArrayUtils.isEmpty(Arrays.copyOfRange(array, 1, 1))).isTrue();
        assertThat(ArrayUtils.isEmpty(Arrays.copyOfRange(array, 1, 2))).isFalse();
    }
}
