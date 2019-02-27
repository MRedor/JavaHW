package me.mredor.calculator;

import org.mockito.InOrder;
import org.junit.jupiter.api.Test;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CalculatorTest {
    private Stack<Double> stack;

    @Test
    void calculateSimple() {
        stack = mock(Stack.class);

        when(stack.pop()).thenReturn(7.0);
        Calculator calculator = new Calculator(stack);
        assertEquals(7.0, calculator.calculate("7"));
        InOrder inOrder = inOrder(stack);
        inOrder.verify(stack).push(7.0);
    }

    @Test
    void calculate() {
        stack = mock(Stack.class);
        when(stack.pop())
                .thenReturn(3.0, 2.0, 3.0, 5.0, 15.0);
        Calculator calculator = new Calculator(stack);

        assertEquals(15.0, calculator.calculate("23+3*"));

        InOrder inOrder = inOrder(stack);
        inOrder.verify(stack).push(2.0);
        inOrder.verify(stack).push(3.0);
        inOrder.verify(stack).push(5.0);
        inOrder.verify(stack).push(3.0);
        inOrder.verify(stack).push(15.0);
    }
}