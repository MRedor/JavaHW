package me.mredor.hw.qsort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class QSortTest {
    private Integer[] makeArray(int size) {
        var random = new Random(size);
        var array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt();
        }
        return array;
    }

    @Test
    void testParallelSortVerySmall() throws InterruptedException {
        var arrayMySort = makeArray(10);
        var arrayStandartSort = arrayMySort.clone();
        var arrayMySingleThreadSort = arrayMySort.clone();
        var startTime = System.nanoTime();
        QSort.parallelSort(arrayMySort);
        var endTime = System.nanoTime();
        System.out.println("Size 10");
        System.out.println("MultiThread: " + ((endTime - startTime) / 1e9) + " s.");
        startTime = System.nanoTime();
        QSort.qSort(arrayMySingleThreadSort);
        endTime = System.nanoTime();
        System.out.println("SingleThread: " + ((endTime - startTime) / 1e9) + " s.");
        Arrays.sort(arrayStandartSort);
        assertArrayEquals(arrayStandartSort, arrayMySingleThreadSort);
        assertArrayEquals(arrayStandartSort, arrayMySort);
    }

    @Test
    void testParallelSortSmall() throws InterruptedException {
        var arrayMySort = makeArray(10000);
        var arrayStandartSort = arrayMySort.clone();
        var arrayMySingleThreadSort = arrayMySort.clone();
        var startTime = System.nanoTime();
        QSort.parallelSort(arrayMySort);
        var endTime = System.nanoTime();
        System.out.println("Size 10000");
        System.out.println("MultiThread: " + ((endTime - startTime) / 1e9) + " s.");
        startTime = System.nanoTime();
        QSort.qSort(arrayMySingleThreadSort);
        endTime = System.nanoTime();
        System.out.println("SingleThread: " + ((endTime - startTime) / 1e9) + " s.");
        Arrays.sort(arrayStandartSort);
        assertArrayEquals(arrayStandartSort, arrayMySingleThreadSort);
        assertArrayEquals(arrayStandartSort, arrayMySort);
    }

    @Test
    void testParallelSortBig() throws InterruptedException {
        var arrayMySort = makeArray(1000000);
        var arrayStandartSort = arrayMySort.clone();
        var arrayMySingleThreadSort = arrayMySort.clone();
        var startTime = System.nanoTime();
        QSort.parallelSort(arrayMySort);
        var endTime = System.nanoTime();
        System.out.println("Size 1000000");
        System.out.println("MultiThread: " + ((endTime - startTime) / 1e9) + " s.");
        startTime = System.nanoTime();
        QSort.qSort(arrayMySingleThreadSort);
        endTime = System.nanoTime();
        System.out.println("SingleThread: " + ((endTime - startTime) / 1e9) + " s.");
        Arrays.sort(arrayStandartSort);
        assertArrayEquals(arrayStandartSort, arrayMySingleThreadSort);
        assertArrayEquals(arrayStandartSort, arrayMySort);
    }

    @Test
    void testFindSizeWhenParallelSortIsFaster() throws InterruptedException {
        for (int size = 1001; size < 100000; size++) {
            var arrayMySort = makeArray(size);
            var arrayStandartSort = arrayMySort.clone();
            var arrayMySingleThreadSort = arrayMySort.clone();
            var startTime = System.nanoTime();
            QSort.parallelSort(arrayMySort);
            var endTime = System.nanoTime();
            var multiThreadTime = endTime - startTime;
            startTime = System.nanoTime();
            QSort.qSort(arrayMySingleThreadSort);
            endTime = System.nanoTime();
            var singleThreadTime = endTime - startTime;
            if (multiThreadTime < singleThreadTime) {
                System.out.println("Parallel Sort is faster when size >= " + size);
                break;
            }
            Arrays.sort(arrayStandartSort);
            assertArrayEquals(arrayStandartSort, arrayMySingleThreadSort);
            assertArrayEquals(arrayStandartSort, arrayMySort);
        }
    }
}