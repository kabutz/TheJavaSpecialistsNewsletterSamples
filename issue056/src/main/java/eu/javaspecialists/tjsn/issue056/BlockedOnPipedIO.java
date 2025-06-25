package eu.javaspecialists.tjsn.issue056;

import java.io.*;

public class BlockedOnPipedIO {
    public static void main(String[] args)
            throws IOException, InterruptedException {
        PipedInputStream in =
                new PipedInputStream(new PipedOutputStream());
        Thread t = new BlockedOnIO(in);
        t.start();
        Thread.sleep(5000);
        t.interrupt();
    }
}
