package me.mredor.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() {
        while (true) {
            try {
                final Socket socket = serverSocket.accept();
                var thread = new Thread(() -> {
                    try (var in = new DataInputStream(socket.getInputStream()); var out = new DataOutputStream(socket.getOutputStream())) {
                        while (!Thread.interrupted()) {
                            var request = in.readInt();
                            if (request == 0) {
                                list(in.readUTF(), out);
                            } else {
                                get(in.readUTF(), out);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private void list(String path, DataOutputStream streamOut) throws IOException {
        var file = new File(path);
        var files = file.listFiles();
        if (files == null) {
            streamOut.writeInt(0);
            streamOut.flush();
            return;
        }
        streamOut.writeInt(files.length);
        for (var current : files) {
            streamOut.writeUTF(current.getName());
            streamOut.writeBoolean(current.isDirectory());
        }
        streamOut.flush();
    }

    private void get(String path, DataOutputStream streamOut) throws IOException {
        var file = new File(path);
        var stream = new DataInputStream(new FileInputStream(file));
        byte[] bytes = new byte[(int) file.length()];
        stream.read(bytes);
        streamOut.writeInt((int) file.length());
        streamOut.write(bytes);
        stream.close();
    }
}
