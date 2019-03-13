package me.mredor.hashmap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HashMapTest {

    private HashMap<String, String> map;

    @BeforeEach
    void createMap() {
        map = new HashMap<String, String>();
    }

    private void firstPut() {
        map.put("M", "Redor");
    }

    private void secondPut() {
        map.put("N", "Sfeps");
    }

    private void thirdPut() {
        map.put("O", "Tgfqt");
    }

    @Test
    void sizeEmpty() {
        assertEquals(0, map.size());
    }

    @Test
    void sizeOfOnePut() {
        firstPut();
        assertEquals(1, map.size());
    }

    @Test
    void sizeOfTwoPuts() {
        firstPut();
        secondPut();
        assertEquals(2, map.size());
    }

    @Test
    void sizeOfThreePuts() {
        firstPut();
        secondPut();
        thirdPut();
        assertEquals(3, map.size());
    }

    @Test
    void sizeOfTwoPutsWithCollision() {
        firstPut();
        thirdPut();
        assertEquals(2, map.size());
    }

    @Test
    void containsKeyTrue() {
        firstPut();
        assertTrue(map.containsKey("M"));
        assertTrue(map.containsValue("Redor"));
    }

    @Test
    void getSimple() {
        firstPut();
        assertEquals("Redor", map.get("M"));
    }

    @Test
    void getNull() {
        firstPut();
        assertEquals(null, map.get("N"));
    }

    @Test
    void getAfterRerecord() {
        firstPut();
        map.put("M", "ary");
        assertEquals("ary", map.get("M"));
    }

    @Test
    void remove() {
        firstPut();
        secondPut();
        map.remove("M");
        assertFalse(map.containsKey("M"));
        assertTrue(map.containsKey("N"));
    }

    @Test
    void clear() {
        map.clear();
        assertEquals(0, map.size());
        firstPut();
        map.clear();
        assertFalse(map.containsKey("M"));
        assertEquals(0, map.size());
    }

    @Test
    void testEntrySet() {
        firstPut();
        secondPut();
        thirdPut();

        String[] keys = new String[3];
        String[] values = new String[3];
        int i = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            keys[i] = entry.getKey();
            values[i] = entry.getValue();
            i++;
        }

        assertEquals("M", keys[0]);
        assertEquals("N", keys[1]);
        assertEquals("O", keys[2]);
        assertEquals("Redor", values[0]);
        assertEquals("Sfeps", values[1]);
        assertEquals("Tgfqt", values[2]);
    }
}
