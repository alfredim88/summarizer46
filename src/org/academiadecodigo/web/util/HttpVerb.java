package org.academiadecodigo.web.util;

import org.academiadecodigo.web.handlers.Get;
import org.academiadecodigo.web.handlers.Handler;
import org.academiadecodigo.web.handlers.Unsupported;

public enum HttpVerb {
    GET(new Get()),
    UNSUPPORTED(new Unsupported());

    private Handler handler;

    HttpVerb(Handler handler) {
        this.handler = handler;
    }

    public static HttpVerb getFromString(String verb) {
        switch (verb) {
            case "GET":
                return GET;
            default:
                return UNSUPPORTED;
        }
    }

    public Handler getHandler() {
        return this.handler;
    }
}
