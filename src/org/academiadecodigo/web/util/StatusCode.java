package org.academiadecodigo.web.util;

public enum StatusCode {
    OK("OK", 200),
    NOT_FOUND("Not Found", 404),
    METHOD_NOT_ALLOWED("Method Not Allowed", 405);

    private String definition;
    private int code;

    StatusCode(String definition, int code) {
        this.definition = definition;
        this.code = code;
    }

    @Override
    public String toString() {
        return this.code + " " + this.definition;
    }
}
