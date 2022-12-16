package com.google.common.collect;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ComparatorsMinTest {
    @Test
    public void testMin() {
        final Calendar calendar = Calendar.getInstance();
        final Date nonNullComparable1 = calendar.getTime();
        final Date nonNullComparable2 = calendar.getTime();

        calendar.set( Calendar.YEAR, calendar.get( Calendar.YEAR ) -1 );
        final Date minComparable = calendar.getTime();

        assertNotSame( nonNullComparable1, nonNullComparable2 );

        try {
            Comparators.min( null, nonNullComparable1 );
            fail();
        } catch (NullPointerException e) {}

        try {
            Comparators.min( nonNullComparable1, null );
            fail();
        } catch (NullPointerException e) {}

        assertSame( nonNullComparable1, Comparators.min( nonNullComparable1, nonNullComparable2 ) );
        assertSame( nonNullComparable2, Comparators.min( nonNullComparable2, nonNullComparable1 ) );
        assertSame( minComparable, Comparators.min( nonNullComparable1, minComparable ) );
        assertSame( minComparable, Comparators.min( minComparable, nonNullComparable1 ) );

        try {
            Comparators.min(null, null);
            fail();
        } catch (NullPointerException e) {}
    }
}
