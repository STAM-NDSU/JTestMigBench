package com.google.common.primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ImmutableIntArrayIsEmptyTest {
    @Test
    public void testIsEmptyPrimitives() {
        final int[] emptyIntArray = {};
        final int[] notEmptyIntArray = {1};
        ImmutableIntArray immutableIntArray;

        try {
            ImmutableIntArray.copyOf((int[]) null);
            fail();
        } catch (NullPointerException e) {}

        immutableIntArray = ImmutableIntArray.copyOf(emptyIntArray);
        assertTrue(immutableIntArray.isEmpty());

        immutableIntArray = ImmutableIntArray.copyOf(notEmptyIntArray);
        assertFalse(immutableIntArray.isEmpty());
    }
}
