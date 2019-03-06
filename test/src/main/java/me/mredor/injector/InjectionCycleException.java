package me.mredor.injector;

public class InjectionCycleException extends Exception {
    public InjectionCycleException(String message) {
        super(message);
    }
}
