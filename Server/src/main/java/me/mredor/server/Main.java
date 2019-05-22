package me.mredor.server;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        var in = new Scanner(System.in);
        System.out.print("port for server?");
        var port = in.nextInt();
        var server = new Server(port);
        var thread = new Thread(server::start);
        thread.start();
        System.out.print("port for client?");
        port = in.nextInt();
        var client = new Client("localhost", port);

        System.out.println("print 1 for list, then print path");
        System.out.println("print 2 for get, then print path for input file and path for output file");

        while (true) {
            var number = in.nextInt();
            if (number == 1) {
                var path = in.next();
                var files = client.list(path);
                for (var current : files) {
                    System.out.println(current.getFirst() + " - " + current.getSecond());
                }
            }
            if (number == 2) {
                var path = in.next();
                var file = in.next();
                client.get(path, file);
            }
        }

    }
}
