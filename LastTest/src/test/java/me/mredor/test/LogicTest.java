package me.mredor.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogicTest {
    Logic logic;
    int size = 4;

    @BeforeEach
    public void create() {
        logic = new Logic(size);
    }

    @Test
    public void testCreateAndFill() {
        assertEquals(size, logic.getSize());
        var used = new int[size * size / 2];
        for (int i = 0; i < size * size / 2; i++) {
            used[i] = 0;
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                used[logic.getCell(i, j)]++;
            }
        }
        for (int i = 0; i < size * size / 2; i++) {
            assertEquals(2, used[i]);
        }
    }

    @Test
    public void testOpen() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                assertFalse(logic.isOpened(i, j));
            }
        }
        int x = 0;
        int y = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (logic.getCell(i, j) == logic.getCell(x, y)) {
                    x = i;
                    y = j;
                }
            }
        }
        assertFalse(x == 0 && y == 0);
        assertTrue(logic.open(0, 0, x, y));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ((i == x && j == y) || (i == 0 && j == 0)) {
                    assertTrue(logic.isOpened(i, j));
                    continue;
                }
                assertFalse(logic.isOpened(i, j));
            }
        }
    }

    @Test
    public void testWin() {
        for (int k = 0; k < size * size / 2; k++) {
            int x1 = -1;
            int y1 = -1;
            int x2 = -1;
            int y2 = -1;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (logic.getCell(i, j) == k) {
                        if (x1 == -1) {
                            x1 = i;
                            y1 = j;
                        } else  {
                            x2 = i;
                            y2 = j;
                        }
                    }
                }
            }
            assertTrue(logic.open(x1, y1, x2, y2));
        }
        assertTrue(logic.checkWin());
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                assertTrue(logic.isOpened(i, j));
            }
        }
    }

    @Test
    public void testEmpty() {
        logic = new Logic(0);
        assertTrue(logic.checkWin());
    }

}