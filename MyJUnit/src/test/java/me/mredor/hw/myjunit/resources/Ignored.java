package me.mredor.hw.myjunit.resources;

import me.mredor.hw.myjunit.annotations.Test;

public class Ignored {
    @Test(ignore = "reason.")
    void test() {
    }
}