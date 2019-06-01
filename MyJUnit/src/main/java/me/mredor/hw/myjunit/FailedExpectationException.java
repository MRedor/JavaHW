package me.mredor.hw.myjunit;

/** Exception class. Special exception thrown if method has expected but throws another Throwable */
public class FailedExpectationException extends Exception {
    public FailedExpectationException() {
        super("Method throws smth unexpected.");
    }
    public FailedExpectationException(String message) {
        super(message);
    }
}
