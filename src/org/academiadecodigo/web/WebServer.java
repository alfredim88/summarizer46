package org.academiadecodigo.web;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WebServer {

    public static final String RESOURCES_ROOT = "www";

    private static final Logger logger = Logger.getLogger(WebServer.class.getName());
    private ServerSocket socket;


    public WebServer(int port) throws IOException {
        //logger.getParent().setLevel(Level.FINEST);
        logger.log(Level.INFO, "Starting WebServer. Binding to port " + port);
        socket = new ServerSocket(port);
    }


    public void start() {
        double requestsServed = 0;

        logger.log(Level.INFO, "Listening for connections");

        try {
            while (true) {
                new Thread(new Client(socket.accept())).start();
                logger.log(Level.INFO, "Requests Served: " + ++requestsServed);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error accepting client: " + e.getMessage());
            restart();
        }
    }

    private void restart() {
        logger.log(Level.WARNING, "Restarting...");
        int port = socket.getLocalPort();

        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Unable to close server socket: " + e.getMessage());
        }

        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Unable to restart server: " + e.getMessage());
            logger.log(Level.SEVERE, "Shutting down..");
            System.exit(1);
        }
    }
}
