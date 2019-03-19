package me.mredor.hw.qsort;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/** Implements quickSort */
public class QSort {
    private static final int MIN_SIZE = 1000;

    /** Ordinary single threaded quick sort
     * @param <T> type of elements in array
     * */
    public static <T extends Comparable<? super T>> void qSort(@NotNull T[] array) {
        sort(array, 0, array.length);
    }

    private static <T extends Comparable<? super T>> void sort(@NotNull T[] array, int begin, int end) {
        if (end - 1 <= begin) {
            return;
        }
        var middle = partition(array, begin, end);
        sort(array, begin, middle);
        sort(array, middle, end);
    }

    /** Multi threaded quick sort. Runs ordinary sort on parts of array with less than 1000 elements
     * @param <T> type of element in array
     * @throws InterruptedException if there are some problems with threads
     * */
    public static <T extends Comparable<? super T>> void parallelSort(@NotNull T[] array) throws InterruptedException {
        var executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        var numberOfTasks = new AtomicInteger(1);
        executor.execute(new Task<>(array, 0, array.length, executor, numberOfTasks));
        synchronized (numberOfTasks) {
            while (numberOfTasks.get() > 0) {
                numberOfTasks.wait();
            }
        }
        executor.shutdown();
    }

    private static <T extends Comparable<? super T>> int partition(@NotNull T[] array, int begin, int end) {
        var element = array[(begin + end - 1) / 2];
        var i = begin;
        var j = end - 1;
        while (i <= j) {
            while (array[i].compareTo(element) < 0) {
                i++;
            }
            while (0 < array[j].compareTo(element)) {
                j--;
            }
            if (i <= j) {
                var swap = array[i];
                array[i] = array[j];
                array[j] = swap;
                i++;
                j--;
            }
        }
        return i;
    }

    private static class Task<T extends Comparable<? super T>> implements Runnable {
        private final T[] array;
        private final int begin;
        private final int end;
        private final ExecutorService executor;
        private final AtomicInteger numberOfTasks;


        private Task(T[] array, int begin, int end, ExecutorService executor, AtomicInteger numberOfTasks) {
            this.array = array;
            this.begin = begin;
            this.end = end;
            this.executor = executor;
            this.numberOfTasks = numberOfTasks;
        }

        @Override
        public void run() {
            if (end - begin < MIN_SIZE) {
                sort(array, begin, end);
            } else {
                var middle = partition(array, begin, end);
                numberOfTasks.addAndGet(2);
                executor.execute(new Task<>(array, begin, middle, executor, numberOfTasks));
                executor.execute(new Task<>(array, middle, end, executor, numberOfTasks));
            }
            if (numberOfTasks.decrementAndGet() == 0) {
                synchronized (numberOfTasks) {
                    numberOfTasks.notify();
                }
            }
        }
    }
}
