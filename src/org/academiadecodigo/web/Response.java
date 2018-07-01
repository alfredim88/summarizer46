package org.academiadecodigo.web;

import org.academiadecodigo.web.util.MimeType;
import org.academiadecodigo.web.util.StatusCode;
import org.academiadecodigo.web.util.Stream;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Response {

    private static final Logger logger = Logger.getLogger(Response.class.getName());

    private File resource;
    private StatusCode code;

    public Response(String requestPath, StatusCode code) {
        this.resource = new File(WebServer.RESOURCES_ROOT + requestPath);

        if (this.resource.isDirectory()) {
            this.resource = new File(this.resource.getAbsolutePath() + "/index.html");
        }

        this.code = code;
    }

    public String getHeader() {
        MimeType type = this.resource != null ? MimeType.getFromFile(this.resource) : null;

        return "HTTP/1.0 " + code + "\r\n" +
                (type == null ? "" : "Content-Type: " + type + "\r\n") +
                "Content-Length: " + (this.resource != null ? this.resource.length() : 0) + "\r\n\r\n";
    }

    public void send(OutputStream output) throws IOException {
        String header = getHeader();

        logger.log(Level.FINE, "Dumping response.");
        logger.log(Level.FINER, "Dumping response header.");
        logger.log(Level.FINER, "Response Header:\n" + header);
        Stream.dump(header, output);

        InputStream file = new FileInputStream(this.resource);
        logger.log(Level.FINER, "Dumping resource " + this.resource.getName());
        Stream.dump(file, output);
    }

    public void removeResource() {
        this.resource = null;
    }

    public File getResource() {
        return this.resource;
    }
}
