package me.mredor.hw.threadpool;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * Special interface of tasks using for interaction with thread pool.
 * @param <T> type of result that task returns
 */
public interface LightFuture<T> {
    /**
     * Checks if task has been executed
     * @return 'true' if task has been executed and 'false' otherwise
     */
    boolean isReady();

    /**
     * Gets result of the execution
     * @throws LightExecutionException if exception occurred during task processing
     */
    T get() throws LightExecutionException;


    /**
     * Applies function to the result of task
     * @return the result of application
     */
    LightFuture<T> thenApply(@NotNull Function<T, T> function);

    /** Exception that is thrown if something happened during execution */
    class LightExecutionException extends Exception {
        LightExecutionException(Exception e) {
            super(e);
        }
    }
}
