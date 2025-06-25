package eu.javaspecialists.tjsn.issue166;

import java.io.*;

public class SuperFastWriter {
    private static final long TERA_BYTE =
            1024L * 1024 * 1024 * 1024;

    public static void main(String... args) throws IOException {
        long bytesWritten = 0;
        byte[] data = new byte[100 * 1024 * 1024];
        ObjectOutputStream out = new ObjectOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream("bigdata.bin")
                )
        );
        long time = System.currentTimeMillis();
        for (int i = 0; i < 10 * 1024 * 1024; i++) {
            out.writeObject(data);
            bytesWritten += data.length;
        }
        out.writeObject(null);
        out.close();
        time = System.currentTimeMillis() - time;
        System.out.printf("Wrote %d TB%n", bytesWritten / TERA_BYTE);
        System.out.println("time = " + time);
    }
}
