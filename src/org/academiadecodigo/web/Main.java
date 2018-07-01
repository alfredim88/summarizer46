package org.academiadecodigo.web;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        int port = args.length == 0 ? 8080 : Integer.parseInt(args[0]);

        try {
            WebServer server = new WebServer(port);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
