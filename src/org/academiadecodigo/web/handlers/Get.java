package org.academiadecodigo.web.handlers;

import org.academiadecodigo.web.Request;
import org.academiadecodigo.web.Response;
import org.academiadecodigo.web.util.StatusCode;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Get implements Handler {

    public static final String NOT_FOUND = "/not_found.html";
    private static final Logger logger = Logger.getLogger(Get.class.getName());

    @Override
    public void handle(Request request, OutputStream client) {
        logger.log(Level.INFO, request.getHttpVerb() + " " + request.getRequestPath());

        Response response = new Response(request.getRequestPath(), StatusCode.OK);

        if (!response.getResource().exists()) {
            logger.log(Level.FINE, "Resource not found: " + request.getRequestPath());
            response = new Response(NOT_FOUND, StatusCode.NOT_FOUND);
        }

        try {
            response.send(client);
        } catch (FileNotFoundException e) {
            logger.log(Level.WARNING, "Cant find the file to send. Sending empty payload...");
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error sending response to client:" + e.getMessage());
        }
    }
}
