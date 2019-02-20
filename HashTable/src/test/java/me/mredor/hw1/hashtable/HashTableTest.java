package me.mredor.hw1.hashtable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {
    private HashTable table;

    @BeforeEach
    void createTable() {
        table = new HashTable();
    }

    private void firstPut() {
        table.put("M", "Redor");
    }

    private void secondPut() {
        table.put("N", "Sfeps");
    }

    private void thirdPut() {
        table.put("O", "Tgfqt");
    }

    @Test
    void sizeEmpty() {
        assertEquals(0, table.size());
    }

    @Test
    void sizeOfOnePut() {
        firstPut();
        assertEquals(1, table.size());
    }

    @Test
    void sizeOfTwoPuts() {
        firstPut();
        secondPut();
        assertEquals(2, table.size());
    }

    @Test
    void sizeOfThreePuts() {
        firstPut();
        secondPut();
        thirdPut();
        assertEquals(3, table.size());
    }

    @Test
    void sizeOfTwoPutsWithCollision() {
        firstPut();
        thirdPut();
        assertEquals(2, table.size());
    }

    @Test
    void containsTrue() {
        firstPut();
        assertTrue(table.contains("M"));
    }

    @Test
    void getSimple() {
        firstPut();
        assertEquals("Redor", table.get("M"));
    }

    @Test
    void getNull() {
        firstPut();
        assertEquals(null, table.get("N"));
    }

    @Test
    void getAfterRerecord() {
        firstPut();
        table.put("M", "ary");
        assertEquals("ary", table.get("M"));
    }

    @Test
    void remove() {
        firstPut();
        secondPut();
        table.remove("M");
        assertFalse(table.contains("M"));
        assertTrue(table.contains("N"));
    }

    @Test
    void clear() {
        table.clear();
        assertEquals(0, table.size());
        firstPut();
        table.clear();
        assertFalse(table.contains("M"));
        assertEquals(0, table.size());
    }
}