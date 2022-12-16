package org.apache.commons.lang3;

import static com.google.common.truth.Truth.assertThat;

import junit.framework.TestCase;

public class ObjectUtilsMaxTest extends TestCase {
    public void testMinMaxNatural() {
        assertThat(ObjectUtils.max(1, 2)).isEqualTo(2);
        assertThat(ObjectUtils.max(2, 1)).isEqualTo(2);
    }

    public void testMinMaxNatural_equalInstances() {
        Foo a = new Foo(1);
        Foo b = new Foo(1);
        assertThat(ObjectUtils.max(a, b)).isSameInstanceAs(a);
    }


    private static class Foo implements Comparable<Foo> {
        final Integer value;

        Foo(int value) {
            this.value = value;
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            return (o instanceof Foo) && ((Foo) o).value.equals(value);
        }

        @Override
        public int compareTo(Foo other) {
            return value.compareTo(other.value);
        }
    }
}
