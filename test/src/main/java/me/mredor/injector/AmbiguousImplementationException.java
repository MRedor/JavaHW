package me.mredor.injector;

public class AmbiguousImplementationException extends Exception {
    public AmbiguousImplementationException(String message) {
        super(message);
    }
}