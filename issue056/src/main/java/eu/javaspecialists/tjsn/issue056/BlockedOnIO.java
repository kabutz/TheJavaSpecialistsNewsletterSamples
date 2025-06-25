package eu.javaspecialists.tjsn.issue056;

import java.io.*;

public class BlockedOnIO extends Thread {
    private final InputStream in;

    public BlockedOnIO(InputStream in) {
        this.in = in;
    }

    public void interrupt() {
        super.interrupt();
        try {
            in.close();
        } catch (IOException e) {} // quietly close
    }

    public void run() {
        try {
            System.out.println("Reading from input stream");
            in.read();
            System.out.println("Finished reading");
        } catch (InterruptedIOException e) {
            Thread.currentThread().interrupt();
            System.out.println("Interrupted via InterruptedIOException");
        } catch (IOException e) {
            if (!isInterrupted()) {
                e.printStackTrace();
            } else {
                System.out.println("Interrupted");
            }
        }
        System.out.println("Shutting down thread");
    }
}
