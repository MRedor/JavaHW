package me.mredor.hw1.hashtable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyListTest {
    private MyList list;

    @BeforeEach
    void createList() {
        list = new MyList();
    }

    private void fill() {
        for (int i = 0; i < 5; i++) {
            list.put(String.valueOf(i) , String.valueOf(i));
        }
    }

    @Test
    void sizeEmpty() {
        assertEquals(0, list.size());
    }

    @Test
    void size() {
        fill();
        assertEquals(5, list.size());
    }

    @Test
    void clear() {
        fill();
        list.clear();
        assertEquals(0, list.size());
    }

    @Test
    void containsEmpty() {
        assertFalse(list.contains("3"));
    }

    @Test
    void containsFalse() {
        fill();
        assertFalse(list.contains("5"));
    }

    @Test
    void containsTrue() {
        fill();
        assertTrue(list.contains("3"));
    }

    @Test
    void removeBad() {
        fill();
        list.remove("5");
        assertEquals(5, list.size());
    }

    @Test
    void remove() {
        fill();
        list.remove("3");
        assertFalse(list.contains("3"));
        assertEquals(4, list.size());
    }

    @Test
    void getKey() {
        fill();
        assertEquals("3", list.getKey(3));
    }

    @Test
    void getValue() {
        fill();
        assertEquals("3", list.getValue("3"));
    }
}