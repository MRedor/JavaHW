package me.mredor.test.md5;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;


/** Special class to calculate MD5 hash of directory or file. With 2 different ways: in 1 thread or using fork-join pool */
public class MD5 {
    private ForkJoinPool pool;

    /** Creates MD5 hasher. */
    public MD5() {
        pool = new ForkJoinPool();
    }

    /** Calculates hash of given file in one thread. */
    public byte[] getSingleThread(File file) throws IOException, NoSuchAlgorithmException {
        if (file.isFile()) {
            return getHashOfFile(file);
        } else {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(file.getName().getBytes());
            var files = file.listFiles();
            Arrays.sort(files, Comparator.comparing(File::getName));
            var results = new byte[files.length + 1][];
            results[0] = digest.digest();
            var size = results[0].length;

            for (int i = 0; i < files.length; i++) {
                results[i + 1] = getSingleThread(files[i]);
                size += results[i + 1].length;
            }

            var result = new byte[size + 1];
            size = 0;
            for (var current : results) {
                System.arraycopy(current, 0, result, size, current.length);
                size += current.length;
            }
            return result;
        }
    }

    private byte[] getHashOfFile(File file) throws IOException, NoSuchAlgorithmException {
        var input = new DigestInputStream(new FileInputStream(file), MessageDigest.getInstance("MD5"));
        var content = new byte[4096];
        for (int read = input.read(content); read != -1; read = input.read(content));
        return input.getMessageDigest().digest();
    }

    /** Calculates hash of given file with many threads. */
    public byte[] getMultithread(File file) throws IOException, NoSuchAlgorithmException {
        if (file.isFile()) {
            return getHashOfFile(file);
        } else {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(file.getName().getBytes());
            var files = file.listFiles();
            Arrays.sort(files, Comparator.comparing(File::getName));
            var results = new byte[files.length + 1][];
            results[0] = digest.digest();

            ArrayList<ForkJoinTask> tasks = new ArrayList<>();
            for (int i = 0; i < files.length; i++) {
                final int j = i;
                tasks.add(pool.submit(() -> results[j + 1] = getMultithread(files[j])));
            }
            for (var task : tasks) {
                task.join();
            }

            int size = 0;
            for (var instance : results) {
                size += instance.length;
            }

            var result = new byte[size + 1];
            size = 0;
            for (var current : results) {
                System.arraycopy(current, 0, result, size, current.length);
                size += current.length;
            }
            return result;
        }
    }

    /** Compares the speed of calculating with one thread and with fork-join pool */
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        var file = new File(args[0]);
        var md5 = new MD5();
        var time = System.currentTimeMillis();
        var result1 = md5.getSingleThread(file);
        System.out.println("singlethread: ");
        System.out.print(System.currentTimeMillis() - time);
        System.out.println(" ms");
        time = System.currentTimeMillis();
        var result2 = md5.getMultithread(file);
        System.out.println("multithread: ");
        System.out.print(System.currentTimeMillis() - time);
        System.out.println(" ms");

    }

}
