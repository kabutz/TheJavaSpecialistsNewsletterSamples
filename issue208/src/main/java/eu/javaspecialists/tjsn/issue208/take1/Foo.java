package eu.javaspecialists.tjsn.issue208.take1;

import java.io.*;

public class Foo implements AutoCloseable {
    private final ObjectInputStream in;

    public Foo() throws IOException {
        in = new ObjectInputStream(
                new BufferedInputStream(
                        new FileInputStream("data.bin")));
    }

    public void close() throws IOException {
        in.close();
    }

    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }
}
