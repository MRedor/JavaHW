package me.mredor.hw.myjunit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MyJUnitInvokerTest {
    private ByteArrayOutputStream output;
    private MyJUnitInvoker invoker;

    @BeforeEach
    void initialize(){
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @Test
    void testMultipleAnnotations() {
        invoker = new MyJUnitInvoker(me.mredor.hw.myjunit.resources.MultipleAnnotations.class);
        assertThrows(MultipleAnnotationException.class, () -> invoker.run());
    }

    @Test
    void testEmpty() throws MultipleAnnotationException {
        invoker = new MyJUnitInvoker(me.mredor.hw.myjunit.resources.Empty.class);
        invoker.run();
        assertTrue(output.toString().contains("Test invocation finished with 0 successes, 0 failures and 0 ignores."));
    }

    @Test
    void testIgnoring() throws MultipleAnnotationException {
        invoker = new MyJUnitInvoker(me.mredor.hw.myjunit.resources.Ignored.class);
        invoker.run();
        assertTrue(output.toString().contains("Test test is ignored. Reason: reason."));
    }

    @Test
    void testExpectingWrong() throws MultipleAnnotationException {
        invoker = new MyJUnitInvoker(me.mredor.hw.myjunit.resources.ExpectWrong.class);
        invoker.run();
        assertTrue(output.toString().contains("Test test failed "));
        assertTrue(output.toString().contains("Method should throw java.lang.RuntimeException but throws java.lang.AssertionError"));
        assertTrue(output.toString().contains("Test invocation finished with 0 successes, 1 failures and 0 ignores."));
    }

    @Test
    void testNoExpect() throws MultipleAnnotationException {
        invoker = new MyJUnitInvoker(me.mredor.hw.myjunit.resources.NoExpect.class);
        invoker.run();
        assertTrue(output.toString().contains("Test test failed "));
        assertTrue(output.toString().contains("Method should throw nothing but throws java.lang.AssertionError"));
        assertTrue(output.toString().contains("Test invocation finished with 0 successes, 1 failures and 0 ignores."));
    }

    @Test
    void testNoThrowExpected() throws MultipleAnnotationException {
        invoker = new MyJUnitInvoker(me.mredor.hw.myjunit.resources.NoThrow.class);
        invoker.run();
        assertTrue(output.toString().contains("Test test failed "));
        assertTrue(output.toString().contains("Method should throw java.lang.RuntimeException but throws nothing."));
        assertTrue(output.toString().contains("Test invocation finished with 0 successes, 1 failures and 0 ignores."));
    }

    @Test
    void testManyTests() throws MultipleAnnotationException {
        invoker = new MyJUnitInvoker(me.mredor.hw.myjunit.resources.ManyTests.class);
        invoker.run();
        assertTrue(output.toString().contains("Test test1 is ignored. Reason: Reason1."));
        assertTrue(output.toString().contains("Test test2 passed"));
        assertTrue(output.toString().contains("Test test3 passed"));
        assertTrue(output.toString().contains("Test invocation finished with 2 successes, 0 failures and 1 ignores."));
    }

    @Test
    void testBeforeAfter() throws MultipleAnnotationException {
        invoker = new MyJUnitInvoker(me.mredor.hw.myjunit.resources.BeforeAfter.class);
        invoker.run();
        assertTrue(output.toString().contains("Test test passed"));
        assertTrue(output.toString().contains("Test invocation finished with 1 successes, 0 failures and 0 ignores."));
    }
}