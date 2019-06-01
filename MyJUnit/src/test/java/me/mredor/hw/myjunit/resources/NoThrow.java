package me.mredor.hw.myjunit.resources;

import me.mredor.hw.myjunit.annotations.Test;

public class NoThrow {
    @Test(expected = RuntimeException.class)
    void test() {
    }
}
