package org.academiadecodigo.web.handlers;

import org.academiadecodigo.web.Request;

import java.io.OutputStream;

public interface Handler {

    void handle(Request request, OutputStream outputStream);
}
