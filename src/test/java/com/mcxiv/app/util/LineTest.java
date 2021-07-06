package com.mcxiv.app.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LineTest {

    @Test
    void perpendicularLineTest() {

        // y = x/2 + 1/2
        Line one = Line.fromTwoPoints(1, 1, 3, 2);

        assertEquals(0.5f, one.getSlope());

        assertEquals(new Vector2(1, 1), one.footOfPerpendicular(0, 3));
        assertEquals(new Vector2(3, 2), one.footOfPerpendicular(2, 4));

        // should be y = -2x + 3
        Line two = one.perpendicularAt(1, 1);

        assertEquals(-2f, two.getSlope());

        assertTrue(two.intersects(1, 1));
        assertTrue(two.intersects(0, 3));
        assertTrue(two.intersects(3 / 2f, 0));

    }

    @Test
    void parallelTest() {
        Line a = Line.fromTwoPoints(1, 1, 3, 2);
        System.out.println(a);

        Line b = a.parallelBy(1, 10, 0);
        System.out.println(b);
        assertTrue(a.put(10, 0) * b.put(10, 0) > 0);
        assertTrue(b.put(10, 0) * b.put(5, 0) > 0);
        assertFalse(b.put(-10, 0) * b.put(5, 0) > 0);

        a = Line.fromTwoPoints(1, 1, 2, 3);
        System.out.println(a);
        b = a.parallelBy(1, 0, 10);
        System.out.println(b);
        assertTrue(a.put(0, 10) * b.put(0, 10) > 0);
        assertFalse(b.put(0, 0) * a.put(0, 0) > 0);
        assertTrue(b.put(-10, -10) * b.put(-5, 5) > 0);
    }
}