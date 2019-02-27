package me.mredor.calculator;

import java.util.Stack;

public class Calculator {

    private Stack<Double> values;

    public Calculator() {
        values = new Stack<>();
    }

    public Calculator (Stack<Double> values) {
        this.values = values;
    }


    public double calculate(String expression) {
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (Character.isDigit(c)) {
                values.push((double)(c - '0'));
                continue;
            }

            double b = values.pop();
            double a = values.pop();
            if (c == '+') {
                values.push(a + b);
            }
            if (c == '-') {
                values.push(a - b);
            }
            if (c == '*') {
                values.push(a * b);
            }
            if (c == '/') {
                values.push(a / b);
            }
        }
        return values.pop();
    }


}