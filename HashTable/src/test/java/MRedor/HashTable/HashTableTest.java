package MRedor.HashTable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {

    @Test
    void sizeEmpty() {
        HashTable table = new HashTable();
        assertEquals(0, table.size());
    }

    @Test
    void sizeOfOnePut() {
        HashTable table = new HashTable();
        table.put("M", "Redor");
        assertEquals(1, table.size());
    }

    @Test
    void sizeOfTwoPuts() {
        HashTable table = new HashTable();
        table.put("M", "Redor");
        table.put("N", "Sfeps");
        assertEquals(2, table.size());
    }

    @Test
    void sizeOfThreePuts() {
        HashTable table = new HashTable();
        table.put("M", "Redor");
        table.put("N", "Sfeps");
        table.put("O", "Tgfqt");
        assertEquals(3, table.size());
    }

    @Test
    void sizeOfTwoPutsWithCollision() {
        HashTable table = new HashTable();
        table.put("M", "Redor");
        table.put("O", "Tgfqt");
        assertEquals(2, table.size());
    }

    @Test
    void containsTrue() {
        HashTable table = new HashTable();
        table.put("M", "Redor");
        assertTrue(table.contains("M"));
    }

    @Test
    void getSimple() {
        HashTable table = new HashTable();
        table.put("M", "Redor");
        assertEquals("Redor", table.get("M"));
    }

    @Test
    void getNull() {
        HashTable table = new HashTable();
        table.put("M", "Redor");
        assertEquals(null, table.get("N"));
    }

    @Test
    void getAfterRerecord() {
        HashTable table = new HashTable();
        table.put("M", "Redor");
        table.put("M", "ary");
        assertEquals("ary", table.get("M"));
    }

    @Test
    void remove() {
        HashTable table = new HashTable();
        table.put("M", "Redor");
        table.put("N", "Sfeps");
        table.remove("M");
        assertFalse(table.contains("M"));
        assertTrue(table.contains("N"));
    }

    @Test
    void clear() {
        HashTable table = new HashTable();
        table.clear();
        assertEquals(0, table.size());
        table.put("M", "Redor");
        table.clear();
        assertFalse(table.contains("M"));
        assertEquals(0, table.size());
    }
}