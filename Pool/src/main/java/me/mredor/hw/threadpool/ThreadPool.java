package me.mredor.hw.threadpool;


import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Thread pool with fixed number of threads
 * @param <T> a type of data which will be processed
 */
public class ThreadPool<T> {
    private int threadsNumber;
    private Thread[] threads;
    private final LinkedList<ThreadPoolTask> tasks;

    /** Creates a thread pool with the given number of threads */
    public ThreadPool(int threadsNumber) {
        this.threadsNumber = threadsNumber;
        this.threads = new Thread[this.threadsNumber];
        tasks = new LinkedList<>();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(
                    () -> {
                        while (!Thread.interrupted()) {
                            ThreadPoolTask currentTask;
                            synchronized (tasks) {
                                if (!tasks.isEmpty()) {
                                    currentTask = tasks.removeFirst();
                                    try {
                                        currentTask.result = currentTask.task.get();
                                    } catch (Exception e) {
                                        currentTask.exception = e;
                                    }
                                    currentTask.ready = true;
                                }
                            }
                        }
                    }
            );

            threads[i].start();
        }
    }

    /**
     * Adds a new task with given Supplier for pool
     * @return the created task
     */
    public LightFuture<T> add(@NotNull Supplier<T> supplier) {
        ThreadPoolTask task = new ThreadPoolTask(supplier);
        synchronized (tasks) {
            tasks.addLast(task);
        }
        return task;
    }

    /** Interrupts all threads in the ThreadPool */
    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    private class ThreadPoolTask implements LightFuture<T> {
        private Supplier<T> task;
        private boolean ready;
        private T result;
        private Exception exception = null;

        private ThreadPoolTask(Supplier<T> supplier) {
            this.task = supplier;
        }

        @Override
        public boolean isReady() {
            return ready;
        }

        @Override
        public T get() throws LightExecutionException {
            while (!ready) {
                Thread.yield();
            }
            if (exception != null) {
                throw new LightExecutionException(exception);
            }
            return result;
        }

        @Override
        public LightFuture<T> thenApply(@NotNull Function<T, T> function) {
            return ThreadPool.this.add(() -> {
                try {
                    return function.apply(ThreadPoolTask.this.get());
                } catch (LightExecutionException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
