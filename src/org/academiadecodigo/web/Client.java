package org.academiadecodigo.web;

import org.academiadecodigo.web.handlers.Handler;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client implements Runnable {

    private static final Logger logger = Logger.getLogger(Client.class.getName());
    private final Socket client;

    public static void handle(Socket client) {
        logger.log(Level.INFO, "Handling client: " + client.getInetAddress() + ":" + client.getPort());

        InputStream input;
        OutputStream output;

        try {
            input = new BufferedInputStream(client.getInputStream());
            output = new BufferedOutputStream(client.getOutputStream());
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error opening client streams: " + e.getMessage());
            close(client);
            return;
        }

        try {
            Request request = new Request(readRequest(input));
            Handler requestHandler = request.getHttpVerb().getHandler();
            requestHandler.handle(request, output);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error reading client's request: + " + e.getMessage());
            close(client);
            return;
        }

        try {
            logger.log(Level.FINER, "Flushing client output stream.");
            output.flush();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error flushing client " + client.getInetAddress() + " response: " + e.getMessage());
        } finally {
            close(client);
        }
    }

    public Client(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        handle(this.client);
    }

    private static String readRequest(InputStream inputStream) throws IOException {
        logger.log(Level.FINE, "Reading client's request..");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder request = new StringBuilder();
        String line = reader.readLine();

        while (line != null && !line.isEmpty()) {
            request.append(line).append("\n");
            line = reader.readLine();
        }

        logger.log(Level.FINER, "Client request:\n" + request);
        return request.toString();
    }

    private static void close(Socket socket) {
        if (socket == null) {
            return;
        }

        logger.log(Level.INFO, "Closing client socket: " + socket.getInetAddress());

        try {
            socket.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error closing client " + socket.getInetAddress() + " socket: " + e.getMessage());
        }

    }
}
