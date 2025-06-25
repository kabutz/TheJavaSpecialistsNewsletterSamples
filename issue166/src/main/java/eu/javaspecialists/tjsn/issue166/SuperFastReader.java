package eu.javaspecialists.tjsn.issue166;

import java.io.*;

public class SuperFastReader {
    private static final long TERA_BYTE =
            1024L * 1024 * 1024 * 1024;

    public static void main(String... args) throws Exception {
        long bytesRead = 0;
        ObjectInputStream in = new ObjectInputStream(
                new BufferedInputStream(
                        new FileInputStream("bigdata.bin")
                )
        );
        long time = System.currentTimeMillis();
        byte[] data;
        while ((data = (byte[]) in.readObject()) != null) {
            bytesRead += data.length;
        }
        in.close();
        time = System.currentTimeMillis() - time;
        System.out.printf("Read %d TB%n", bytesRead / TERA_BYTE);
        System.out.println("time = " + time);
    }
}
