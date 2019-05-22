package me.mredor.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {
    Server server;
    Client client;

    @BeforeEach
    public void create() throws IOException {
        server = new Server(8080);
        var thread = new Thread(server::start);
        thread.start();
        client = new Client("localhost", 8080);
    }

    @Test
    public void testGet() throws IOException {
        var copy = client.get("src/resources/dir3/file5", "src/resources/dir3/getFile5");
        var file = new File("src/resources/dir3/getFile5");
        var result = true;
        if (file.length() != copy.length()) {
            result = false;
        }
        var byteFile = new byte[(int) file.length()];
        var byteCopy = new byte[(int) copy.length()];

        var stream1 = new FileInputStream(file);
        stream1.read(byteFile);
        var stream2 = new FileInputStream(copy);
        stream2.read(byteCopy);

        for (int i = 0; i < (int) file.length(); i++) {
            if (byteCopy[i] != byteFile[i]) {
                result = false;
                break;
            }
        }
        assertEquals(true, result);
    }

    @Test
    public void testList() throws IOException {
        var files = client.list("src/resources/dir1");
        var answer = new ArrayList<>();
        answer.add(new Pair<>("dir2", true));
        answer.add(new Pair<>("file3", false));
        answer.add(new Pair<>("file1", false));
        answer.add(new Pair<>("file2", false));
        assertEquals(answer, files);
    }
}