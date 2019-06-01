package me.mredor.hw.myjunit.resources;

import me.mredor.hw.myjunit.annotations.Test;

public class NoExpect {
    @Test()
    void test() {
        throw new AssertionError();
    }
}
