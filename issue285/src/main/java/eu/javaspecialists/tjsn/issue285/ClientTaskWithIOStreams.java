package eu.javaspecialists.tjsn.issue285;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

class ClientTaskWithIOStreams implements Runnable {
    private final Socket socket;
    private final boolean verbose;

    public ClientTaskWithIOStreams(Socket socket, boolean verbose) {
        this.socket = socket;
        this.verbose = verbose;
    }

    private static final String message = "John 3:16";

    public void run() {
        try (socket;
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(
                             socket.getInputStream()));
             PrintStream out = new PrintStream(
                     socket.getOutputStream(), true)
        ) {
            while (true) {
                out.println(message);
                TimeUnit.SECONDS.sleep(2);
                String reply = in.readLine();
                if (verbose) System.out.println(reply);
                TimeUnit.SECONDS.sleep(2);
            }
        } catch (Exception consumeAndExit) {}
    }
}
