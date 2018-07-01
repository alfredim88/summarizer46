package org.academiadecodigo.web.util;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Stream {

    public static Logger logger = Logger.getLogger(Stream.class.getName());

    public static void dump(String input, OutputStream output) throws IOException {
        // decorate the outputstream with a BufferedOutputStream to make sure we are using a buffer
        output.write(input.getBytes());
    }

    public static void dump(InputStream input, OutputStream output) throws IOException {
        logger.log(Level.FINER, "Dumping data to outputstream.");

        byte[] buffer = new byte[512];
        int bytesread = input.read(buffer);

        while (bytesread > -1) {
            logger.log(Level.FINEST, "Writing to outputstream: " + new String(buffer, 0, bytesread));
            output.write(buffer, 0, bytesread);
            bytesread = input.read(buffer);
        }
    }
}
