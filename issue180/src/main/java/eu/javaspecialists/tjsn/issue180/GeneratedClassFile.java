package eu.javaspecialists.tjsn.issue180;

import javax.tools.*;
import java.io.*;
import java.net.*;

class GeneratedClassFile extends SimpleJavaFileObject {
    private final ByteArrayOutputStream outputStream =
            new ByteArrayOutputStream();

    public GeneratedClassFile() {
        super(URI.create("generated.class"), Kind.CLASS);
    }

    public OutputStream openOutputStream() {
        return outputStream;
    }

    public byte[] getClassAsBytes() {
        return outputStream.toByteArray();
    }
}
