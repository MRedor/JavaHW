package me.mredor.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StationTest {
    private Station station;
    private final int CAPACITY = 4;
    private Counter counterIn;

    @BeforeEach
    public void create() {
        station = new Station(CAPACITY);
        counterIn = new Counter();
        counterIn.counter = 0;
    }

    @Test
    public void testNegativeCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new Station(-2));
    }

    @Test
    public void testZeroCapacity() {
        station = new Station(0);
        assertFalse(station.in());
        station.out();
        assertFalse(station.in());
    }

    @Test
    public void testInSingleThread() {
        for (int i = 0; i < CAPACITY; i++) {
            assertTrue(station.in());
        }
        assertFalse(station.in());
        assertFalse(station.in());
    }

    @Test
    public void testOutSingleThread() {
        for (int i = 0; i < CAPACITY; i++) {
            assertTrue(station.in());
        }
        station.out();
        assertTrue(station.in());
        assertFalse(station.in());
    }

    @Test
    public void testMultiThreadSimple() {
        for (int i = 0; i < 4; i++) {
            var thread  = new Thread(() -> assertTrue(station.in()));
            thread.start();
        }
    }

    @Test
    public void testInMultiThread() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            var thread = new Thread(() -> {
                synchronized (counterIn) {
                    if (counterIn.counter >= CAPACITY) {
                        assertFalse(station.in());
                    } else {
                        assertTrue(station.in());
                        counterIn.counter++;
                    }
                }
            });
            thread.start();
        }
        Thread.sleep(1000);
        assertEquals(CAPACITY, counterIn.counter);
    }

    @Test
    public void testInOutMultiThread() {
        for (int i = 0; i < 100; i++) {
            var thread1 = new Thread(() -> {
                synchronized (counterIn) {
                    if (counterIn.counter >= CAPACITY) {
                        assertFalse(station.in());
                    } else {
                        assertTrue(station.in());
                        counterIn.counter++;
                    }
                }
            });
            thread1.start();
            var thread2 = new Thread(() -> {
                synchronized (counterIn) {
                    if (counterIn.counter > 0) {
                        counterIn.counter--;
                    }
                    station.out();
                }
            });
            thread2.start();
        }
    }

    @Test
    public void testMultiThreadStrange() {
        for (int i = 0; i < 100; i++) {
            var thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    station.out();
                    assertTrue(station.in());
                }
            });
        }
    }

    private class Counter {
        public volatile int counter;
    }
}