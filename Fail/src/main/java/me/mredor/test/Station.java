package me.mredor.test;

import java.util.concurrent.atomic.AtomicInteger;

/** Special class for control Parking */
public class Station {
    private int capacity;
    private AtomicInteger counter;

    /** Creates parking with given number of places */
    public Station(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity should be non-negative");
        }
        this.capacity = capacity;
        counter = new AtomicInteger(0);
    }

    /** Checks if the car can move in and allow it or deny (if there is no free place in the parking) */
    public boolean in() {
        return (capacity > counter.getAndUpdate(x -> {
            if (x < capacity) {
                return x + 1;
            } else {
                return x;
            }
        }) );
    }

    /** Allow to move out from parking and count free places */
    public void out() {
        counter.getAndUpdate(x -> {
            if (x > 0) {
                return x - 1;
            } else {
                return x;
            }
        });
    }
}
