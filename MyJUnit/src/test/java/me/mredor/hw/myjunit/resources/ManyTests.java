package me.mredor.hw.myjunit.resources;

import me.mredor.hw.myjunit.annotations.Test;

public class ManyTests {
    @Test(ignore = "Reason1.")
    void test1() {
        throw new AssertionError();
    }

    @Test
    void test2() {

    }

    @Test(expected = RuntimeException.class)
    void test3() {
        throw new RuntimeException();
    }

}
