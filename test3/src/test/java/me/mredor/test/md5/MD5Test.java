package me.mredor.test.md5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class MD5Test {
    MD5 md5;

    @BeforeEach
    void create() {
        md5 = new MD5();
    }

    @Test
    void testEmptySingle() throws IOException, NoSuchAlgorithmException {
        byte[] result = new byte[] {-44, 29, -116, -39, -113, 0, -78, 4, -23, -128, 9, -104, -20, -8, 66, 126};
        var result1 = md5.getSingleThread(new File("src/test/java/me/mredor/test/md5/empty"));
        assertArrayEquals(result, result1);
    }

    @Test
    void testDirSingle() throws IOException, NoSuchAlgorithmException {
        byte[] result = new byte[] {
                115, 96, 7, -125, 45, 33, 103, -70, -86,
                -25, 99, -3, 58, 63, 60, -15, -108,
                7, -76, -108, 63, 25, 26, -69, 87, -2, -33, -108,
                101, 34, 84, 42, 0, -44, 29, -116, -39, -113,
                0, -78, 4, -23, -128, 9, -104, -20, -8, 66, 126, 0};
        var result1 = md5.getSingleThread(new File("src/test/java/me/mredor/test/md5/dir"));
        assertArrayEquals(result, result1);
    }


    @Test
    void testEmptyMulti() throws IOException, NoSuchAlgorithmException {
        byte[] result = new byte[] {-44, 29, -116, -39, -113, 0, -78, 4, -23, -128, 9, -104, -20, -8, 66, 126};
        var result1 = md5.getMultithread(new File("src/test/java/me/mredor/test/md5/empty"));
        assertArrayEquals(result, result1);
    }

    @Test
    void testDirMulti() throws IOException, NoSuchAlgorithmException {
        byte[] result = new byte[] {
                115, 96, 7, -125, 45, 33, 103, -70, -86,
                -25, 99, -3, 58, 63, 60, -15, -108,
                7, -76, -108, 63, 25, 26, -69, 87, -2, -33, -108,
                101, 34, 84, 42, 0, -44, 29, -116, -39, -113,
                0, -78, 4, -23, -128, 9, -104, -20, -8, 66, 126, 0};
        var result1 = md5.getMultithread(new File("src/test/java/me/mredor/test/md5/dir"));
        assertArrayEquals(result, result1);
    }

    @Test
    void testEquals() throws IOException, NoSuchAlgorithmException {
        var result1 = md5.getMultithread(new File("src/test/java/me/mredor/test/md5/dir"));
        var result2 = md5.getSingleThread(new File("src/test/java/me/mredor/test/md5/dir"));
        assertArrayEquals(result1, result2);
    }
}