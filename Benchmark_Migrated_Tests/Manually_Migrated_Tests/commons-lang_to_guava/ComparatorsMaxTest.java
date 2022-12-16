package com.google.common.collect;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ComparatorsMaxTest {
    @Test
    public void testMax() {
        final Calendar calendar = Calendar.getInstance();
        final Date nonNullComparable1 = calendar.getTime();
        final Date nonNullComparable2 = calendar.getTime();

        calendar.set( Calendar.YEAR, calendar.get( Calendar.YEAR ) -1 );
        final Date minComparable = calendar.getTime();

        assertNotSame( nonNullComparable1, nonNullComparable2 );

        try {
            Comparators.max( null, nonNullComparable1 );
            fail();
        } catch (NullPointerException e) {  }

        try {
            Comparators.max( nonNullComparable1, null );
            fail();
        } catch (NullPointerException e) {  }

        assertSame( nonNullComparable1, Comparators.max( nonNullComparable1, nonNullComparable2 ) );
        assertSame( nonNullComparable2, Comparators.max( nonNullComparable2, nonNullComparable1 ) );
        assertSame( nonNullComparable1, Comparators.max( nonNullComparable1, minComparable ) );
        assertSame( nonNullComparable1, Comparators.max( minComparable, nonNullComparable1 ) );

        try {
            Comparators.max(null, null);
            fail();
        } catch (NullPointerException e) {  }
    }
}
