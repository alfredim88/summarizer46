package org.academiadecodigo.web;

import org.academiadecodigo.web.util.HttpVerb;

public class Request {

    private boolean invalid;
    private HttpVerb httpVerb;
    private String requestPath;

    public Request(String data) {
        if (data == null) {
            invalid = true;
            return;
        }

        String[] request = data.split(" ");

        if (request.length < 2) {
            invalid = true;
            return;
        }

        httpVerb = HttpVerb.getFromString(data.split(" ")[0]);
        requestPath = data.split(" ")[1];
    }

    public HttpVerb getHttpVerb() {
        return httpVerb;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public boolean isValid() {
        return !this.invalid;
    }
}
