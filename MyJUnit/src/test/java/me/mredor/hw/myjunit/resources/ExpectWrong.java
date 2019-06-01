package me.mredor.hw.myjunit.resources;

import me.mredor.hw.myjunit.annotations.Test;

public class ExpectWrong {
    @Test(expected = RuntimeException.class)
    void test() {
        throw new AssertionError();
    }
}
