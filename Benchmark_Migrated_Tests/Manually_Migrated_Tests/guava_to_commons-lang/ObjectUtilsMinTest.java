package org.apache.commons.lang3;

import junit.framework.TestCase;

import static com.google.common.truth.Truth.assertThat;

public class ObjectUtilsMinTest extends TestCase {
    public void testMinMaxNatural() {
        assertThat(ObjectUtils.min(1, 2)).isEqualTo(1);
        assertThat(ObjectUtils.min(2, 1)).isEqualTo(1);
    }

    public void testMinMaxNatural_equalInstances() {
        Foo a = new Foo(1);
        Foo b = new Foo(1);
        assertThat(ObjectUtils.min(a, b)).isSameInstanceAs(a);
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
