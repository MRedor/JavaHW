package me.mredor.hw.threadpool;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class ThreadPoolTest {

    @Test
    void testSimple() throws LightFuture.LightExecutionException {
        var pool = new ThreadPool<Integer>(1);
        var task = pool.add(() -> 42);
        assertEquals(42, (int) task.get());
        assertTrue(task.isReady());
    }

    @Test
    void testManyThreads() throws InterruptedException {
        var pool = new ThreadPool<Integer>(10);
        var check = new int[1000];
        for (int i = 0; i < 1000; i++) {
            final int j = i;
            pool.add(() -> check[j] = j);
        }
        Thread.sleep(1000);
        for (int i = 0; i < 1000; i++) {
            assertEquals(i, check[i]);
        }
    }

    @Test
    void testShutdownOneThread() throws InterruptedException {
        var pool = new ThreadPool<Integer>(1);
        pool.shutdown();
        Thread.sleep(1000);
        var task = pool.add(() -> 19);
        Thread.sleep(1000);
        assertFalse(task.isReady());
    }

    @Test
    void testShutdownManyThreads() throws InterruptedException {
        var pool = new ThreadPool<Integer>(10);
        pool.add(() -> 17);
        pool.add(() -> 17);
        pool.shutdown();
        Thread.sleep(1000);
        var task = pool.add(() -> 19);
        Thread.sleep(1000);
        assertFalse(task.isReady());
    }

    @Test
    void testShutdownReflectionCheck() throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        var pool = new ThreadPool<Integer>(10);
        for (int i = 0; i < 7; i++) {
            pool.add(() -> 42);
        }
        var threads = pool.getClass().getDeclaredField("threads");
        threads.setAccessible(true);
        pool.shutdown();
        Thread.sleep(1000);
        for (Thread thread : (Thread[]) threads.get(pool)) {
            assertFalse(thread.isAlive());
        }
    }

    @Test
    void testNumberOfThreads() throws NoSuchFieldException, IllegalAccessException {
        var pool = new ThreadPool<Integer>(10);
        for (int i = 0; i < 8; i++) {
            pool.add(() -> 42);
        }
        var threads = pool.getClass().getDeclaredField("threads");
        threads.setAccessible(true);
        int threadsAlive = 0;
        for (var thread : (Thread[]) threads.get(pool)) {
            if (thread.isAlive()) {
                threadsAlive++;
            }
        }
        assertEquals(10, threadsAlive);
    }

    @Test
    void testOneThreadIsReady() throws LightFuture.LightExecutionException {
        var pool = new ThreadPool<Integer>(1);
        var task = pool.add(() -> 42);
        task.get();
        assertTrue(task.isReady());
    }

    @Test
    void testIsReadyFalse() throws InterruptedException {
        var pool = new ThreadPool<Integer>(1);
        var task = pool.add(() -> {
            while (true) {
                Thread.yield();
            }
        });
        Thread.sleep(1000);
        assertFalse(task.isReady());
    }

    @Test
    void testThrowLightExecutionException() {
        var pool = new ThreadPool<Integer>(10);
        var task = pool.add(() -> {
            throw new RuntimeException("");
        });
        assertThrows(LightFuture.LightExecutionException.class, task::get);
    }

    @Test
    void testThenApplySimple() throws LightFuture.LightExecutionException {
        var pool = new ThreadPool<Integer>(10);
        var task = pool.add(() -> 42);
        assertEquals(42, (int) task.get());
        var task2 = task.thenApply(x -> 2*x);
        var task3 = task.thenApply(x -> x - 1);
        assertEquals(84, (int) task2.get());
        assertEquals(41, (int) task3.get());
    }

    @Test
    void testThenApply() throws LightFuture.LightExecutionException {
        var pool = new ThreadPool<Integer>(10);
        var tasks = new LightFuture[10];
        tasks[0] = pool.add(() -> 1);
        for (int i = 1; i < 10; i++) {
            tasks[i] = tasks[i - 1].thenApply((x) -> (int) x * 2);
        }
        for (int i = 9; i > -1; i--) {
            assertEquals(1 << i, (int) tasks[i].get());
        }
    }

    @Test
    void testThenApplyWithException() {
        var pool = new ThreadPool<Integer>(10);
        var task1 = pool.add(() -> null);
        var task2 = task1.thenApply(x -> x - 1);
        assertThrows(LightFuture.LightExecutionException.class, task2::get);
    }
}