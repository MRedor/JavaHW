package me.mredor.hw.my.treeset;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MyTreeSetTest {
    private MyTreeSet<Integer> set;

    @BeforeEach
    void createSet() {
        set = new MyTreeSet<>();
    }

    void sizeEmpty() {
        assertEquals(0, set.size());
    }

    void size() {
        set.add(19);
        assertEquals(1, set.size());
        set.add(42);
        assertEquals(2, set.size());
        set.add(42);
        assertEquals(2, set.size());
    }

    @Test
    void removeEmpty() {
        assertFalse(set.remove(19));
    }

    @Test
    void removeSingle() {
        set.add(19);
        assertTrue(set.remove(19));
        assertFalse(set.contains(19));
        assertArrayEquals(new Integer[] {}, set.toArray());
    }

    @Test
    void removeDouble() {
        set.add(19);
        assertTrue(set.remove(19));
        assertFalse(set.remove(19));
    }

    @Test
    void firstAscendingOrder() {
        set.addAll(Arrays.asList(1, 2, 3, 4));
        assertEquals(1, set.first().intValue());
        set.remove(1);
        assertEquals(2, set.first().intValue());
    }

    @Test
    void firstDescendingOrder() {
        set.addAll(Arrays.asList(1, 2, 3, 4));
        assertEquals(4, set.descendingSet().first().intValue());
        set.remove(4);
        assertEquals(3, set.descendingSet().first().intValue());
    }

    @Test
    void lastAscendingOrder() {
        set.addAll(Arrays.asList(1, 2, 3, 4));
        assertEquals(4, set.last().intValue());
        set.remove(4);
        assertEquals(3, set.last().intValue());
    }

    @Test
    void lastDescendingOrder() {
        set.addAll(Arrays.asList(1, 2, 3, 4));
        assertEquals(1, set.descendingSet().last().intValue());
        set.remove(1);
        assertEquals(2, set.descendingSet().last().intValue());
    }

    @Test
    void lowerAscendingOrder() {
        set.addAll(Arrays.asList(1, 2, 3, 4));
        assertEquals(Integer.valueOf(1), set.lower(2));
        assertEquals(Integer.valueOf(4), set.lower(5));
        assertEquals(null, set.lower(0));
    }

    @Test
    void lowerDescendingOrder() {
        set.addAll(Arrays.asList(1, 2, 3, 4));
        assertEquals(2, set.descendingSet().lower(1).intValue());
        assertEquals(1, set.descendingSet().lower(0).intValue());
        assertEquals(null, set.descendingSet().lower(5));
    }

    @Test
    void floorAscendingOrder() {
        set.addAll(Arrays.asList(1, 3, 5));
        assertEquals(5, set.floor(5).intValue());
        assertEquals(3, set.floor(4).intValue());
        assertEquals(null, set.floor(0));
    }

    @Test
    void floorDescendingOrder() {
        set.addAll(Arrays.asList(1, 3, 5));
        assertEquals(3, set.descendingSet().floor(3).intValue());
        assertEquals(5, set.descendingSet().floor(4).intValue());
        assertEquals(null, set.descendingSet().floor(6));
    }

    @Test
    void ceilingAscendingOrder() {
        set.addAll(Arrays.asList(1, 3, 5));
        assertEquals(3, set.ceiling(3).intValue());
        assertEquals(5, set.ceiling(4).intValue());
        assertEquals(null, set.ceiling(6));
    }

    @Test
    void ceilingDescendingOrder() {
        set.addAll(Arrays.asList(1, 3, 5));
        assertEquals(5, set.descendingSet().ceiling(5).intValue());
        assertEquals(3, set.descendingSet().ceiling(4).intValue());
        assertEquals(null, set.descendingSet().ceiling(0));
    }

    @Test
    void higherAscendingOrder() {
        set.addAll(Arrays.asList(1, 3, 5));
        assertEquals(5, set.higher(4).intValue());
        assertEquals(5, set.higher(3).intValue());
        assertEquals(null, set.higher(5));
    }

    @Test
    void higherDescendingOrder() {
        set.addAll(Arrays.asList(1, 3, 5));
        assertEquals(3, set.descendingSet().higher(4).intValue());
        assertEquals(1, set.descendingSet().higher(3).intValue());
        assertEquals(null, set.descendingSet().higher(1));
    }

    @Test
    void iteratorEmpty() {
        ArrayList<Integer> array = new ArrayList<>();
        array.addAll(set);
        assertArrayEquals(new Integer[] {}, array.toArray());
    }

    @Test
    void iteratorSimple() {
        set.addAll(Arrays.asList(1, 2, 3, 4, 5));
        ArrayList<Integer> array = new ArrayList<>();
        array.addAll(set);
        assertArrayEquals(new Integer[] {1, 2, 3, 4, 5}, array.toArray());
    }

    @Test
    void iteratorAscendingOrder() {
        set.addAll(Arrays.asList(1, 2, 5, 4, 3));
        ArrayList<Integer> array = new ArrayList<>();
        array.addAll(set);
        assertArrayEquals(new Integer[] {1, 2, 3, 4, 5}, array.toArray());
        Iterator<Integer> iterator = set.iterator();
        for (int i = 1; i <= 5; i++) {
            assertTrue(iterator.hasNext());
            assertEquals(i, iterator.next().intValue());
        }
        assertFalse(iterator.hasNext());
    }

    @Test
    void iteratorDescendingOrder() {
        set.addAll(Arrays.asList(1, 2, 5, 4, 3));
        MyTreeSet<Integer> descendingSet = (MyTreeSet<Integer>) set.descendingSet();
        Iterator<Integer> iterator = descendingSet.iterator();
        for (int i = 5; i >= 1; i--) {
            assertTrue(iterator.hasNext());
            assertEquals(i, iterator.next().intValue());
        }
        assertFalse(iterator.hasNext());
    }

    @Test
    void descendingIteratorAscendingOrder() {
        set.addAll(Arrays.asList(1, 2, 5, 4, 3));
        Iterator<Integer> iterator = set.descendingIterator();
        for (int i = 5; i >= 1; i--) {
            assertTrue(iterator.hasNext());
            assertEquals(i, iterator.next().intValue());
        }
        assertFalse(iterator.hasNext());
    }

    @Test
    void descendingIteratorDescendingOrder() {
        set.addAll(Arrays.asList(1, 2, 5, 4, 3));
        Iterator<Integer> iterator = set.descendingSet().descendingIterator();
        for (int i = 1; i <= 5; i++) {
            assertTrue(iterator.hasNext());
            assertEquals(i, iterator.next().intValue());
        }
        assertFalse(iterator.hasNext());
    }
}