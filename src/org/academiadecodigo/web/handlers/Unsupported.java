package org.academiadecodigo.web.handlers;

import org.academiadecodigo.web.Request;
import org.academiadecodigo.web.Response;
import org.academiadecodigo.web.util.StatusCode;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Unsupported implements Handler{

    public static final String PAGE = "/unsupported.html";
    private static final Logger logger = Logger.getLogger(Unsupported.class.getName());

    @Override
    public void handle(Request request, OutputStream client) {
        logger.log(Level.INFO, request.getHttpVerb() + " " + request.getRequestPath());
        logger.log(Level.FINE, "This request is unsupported.");

        Response response = new Response(PAGE, StatusCode.METHOD_NOT_ALLOWED);

        try {
            response.send(client);
        } catch (FileNotFoundException e) {
            logger.log(Level.WARNING, "Cant find the file to send. Sending empty payload...");
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error sending response to client:" + e.getMessage());
        }
    }
}
