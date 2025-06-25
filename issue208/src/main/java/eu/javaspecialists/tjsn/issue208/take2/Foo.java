package eu.javaspecialists.tjsn.issue208.take2;

import java.io.*;

public class Foo implements AutoCloseable {
    private final ObjectInputStream in =
            new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream("data.bin")));

    public Foo() throws IOException {
    }

    public void close() throws IOException {
        in.close();
    }

    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }
}
