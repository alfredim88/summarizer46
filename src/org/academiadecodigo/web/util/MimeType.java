package org.academiadecodigo.web.util;

import java.io.File;

public enum MimeType {
    JPG("image"),
    JPEG("image"),
    PNG("image"),
    GIF("image"),
    HTML("text"),
    TXT("text", "plain"),
    UNSUPPORTED("application", "octet-stream");

    private String prefix;
    private String type;

    MimeType(String prefix, String type) {
        this.prefix = prefix;
        this.type = type;
    }

    MimeType(String prefix) {
        this.prefix = prefix;
        this.type = this.name().toLowerCase();
    }

    public static MimeType getFromExtension(String ext) {
        MimeType[] types = MimeType.values();

        for (int i = 0; i < types.length; i++) {
            if (!types[i].name().toLowerCase().equals(ext)) {
                continue;
            }

            return types[i];
        }

        return UNSUPPORTED;
    }

    public static MimeType getFromFile(File resource) {
        String filename = resource.getName();
        String ext = filename.substring(filename.lastIndexOf(".") + 1);

        return getFromExtension(ext);
    }

    @Override
    public String toString() {
        return this.prefix + "/" + this.type;
    }
}
