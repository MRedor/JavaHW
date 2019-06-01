package me.mredor.hw.myjunit.resources;

import me.mredor.hw.myjunit.annotations.After;
import me.mredor.hw.myjunit.annotations.Before;
import me.mredor.hw.myjunit.annotations.Test;

public class BeforeAfter {
    int x = 0;
    @Before
    void before() {
        x = 1;
    }

    @Test
    void test() {
        if (x == 0) {
            throw new RuntimeException("Before not works.");
        } else {
            x = 2;
        }
    }

    @After
    void after() {
        if (x != 2) {
            throw new RuntimeException("After works too early");
        }
    }

}
