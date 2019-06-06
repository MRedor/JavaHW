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
    private Thread[] threads;
    private final LinkedList<ThreadPoolTask> tasks;
    private boolean isShutDown;

    /** Creates a thread pool with the given number of threads */
    public ThreadPool(int threadsNumber) {
        this.threads = new Thread[threadsNumber];
        tasks = new LinkedList<>();
        isShutDown = false;
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(
                    () -> {
                        while (!Thread.interrupted()) {
                            ThreadPoolTask currentTask = null;
                            synchronized (tasks) {
                                if (!tasks.isEmpty()) {
                                    currentTask = tasks.removeFirst();
                                }
                            }
                            if (currentTask != null) {
                                try {
                                    currentTask.result = currentTask.task.get();
                                } catch (Exception e) {
                                    currentTask.exception = e;
                                }
                                currentTask.ready = true;
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
        if (isShutDown) {
            throw new IllegalStateException("Thread pool is shut down");
        }
        ThreadPoolTask task = new ThreadPoolTask(supplier);
        synchronized (tasks) {
            tasks.addLast(task);
        }
        return task;
    }

    /** Interrupts all threads in the ThreadPool
     *
     * @throws InterruptedException if there is exception in joining threads
     * */
    public void shutdown() throws InterruptedException {
        for (Thread thread : threads) {
            thread.interrupt();
            thread.join();
        }
        isShutDown = true;
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
            if (isShutDown) {
                throw new IllegalStateException("Thread pool is shut down");
            }
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

