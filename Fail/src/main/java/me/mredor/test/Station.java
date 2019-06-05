package me.mredor.test;

import java.util.concurrent.atomic.AtomicInteger;

public class Station {
    private int capacity;
    private AtomicInteger counter;

    public Station(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity should be non-negative");
        }
        this.capacity = capacity;
        counter = new AtomicInteger(0);
    }

    public boolean in() {
        return (capacity > counter.getAndUpdate(x -> {
            if (x < capacity) {
                return x + 1;
            } else {
                return x;
            }
        }) );
    }

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
