package me.mredor.server;


import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    private DataInputStream streamIn;
    private DataOutputStream streamOut;

    public Client(String host, int port) throws IOException {
        var clientSocket = new Socket(host, port);
        streamIn = new DataInputStream(clientSocket.getInputStream());
        streamOut = new DataOutputStream(clientSocket.getOutputStream());

    }

    public ArrayList<Pair<String, Boolean>> list(String path) throws IOException {
        streamOut.writeInt(0);
        streamOut.writeUTF(path);
        streamOut.flush();
        var files = new ArrayList<Pair<String, Boolean>>();
        var size = streamIn.readInt();
        for (int i = 0; i < size; i++) {
            var name = streamIn.readUTF();
            var isDirectory = streamIn.readBoolean();
            files.add(new Pair<>(name, isDirectory));
        }
        return files;
    }

    public File get(String path, String file) throws IOException {
        streamOut.writeInt(1);
        streamOut.writeUTF(path);
        streamOut.flush();
        var output = new File(file);
        byte[] bytes = new byte[streamIn.readInt()];
        streamIn.read(bytes);
        var stream = new DataOutputStream(new FileOutputStream(output));
        stream.write(bytes);
        return output;
    }
}
