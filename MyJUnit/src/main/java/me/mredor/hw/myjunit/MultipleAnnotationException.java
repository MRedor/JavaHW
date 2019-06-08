package me.mredor.hw.myjunit;

/** Exception class. Special exception thrown if there are more than 1 annotation for 1 method */
public class MultipleAnnotationException extends Exception {
    public MultipleAnnotationException() {
        super("Too many annotations found for one method.");
    }
}
