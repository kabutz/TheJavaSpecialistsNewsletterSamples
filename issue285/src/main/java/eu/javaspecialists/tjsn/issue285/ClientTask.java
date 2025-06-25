package eu.javaspecialists.tjsn.issue285;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

class ClientTask implements Runnable {
    private final Socket socket;
    private final boolean verbose;

    public ClientTask(Socket socket, boolean verbose) {
        this.socket = socket;
        this.verbose = verbose;
    }

    private static final byte[] message = "John 3:16\n".getBytes();

    private static final Appendable INITIAL = new Appendable() {
        public Appendable append(CharSequence csq) {
            return new StringBuilder().append(csq);
        }

        public Appendable append(CharSequence csq, int start, int end) {
            return new StringBuilder().append(csq, start, end);
        }

        public Appendable append(char c) {
            return new StringBuilder().append(c);
        }
    };

    public void run() {
        Appendable appendable = INITIAL;
        try (socket;
             InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream()
        ) {
            while (true) {
                for (byte b : message) {
                    out.write(b);
                }
                out.flush();
                TimeUnit.SECONDS.sleep(2);

                for (int i = 0; i < message.length; i++) {
                    int b = in.read();
                    if (verbose) {
                        appendable = appendable.append((char) b);
                    }
                }
                if (verbose) {
                    System.out.print(appendable);
                    appendable = INITIAL;
                }
                TimeUnit.SECONDS.sleep(2);
            }
        } catch (Exception consumeAndExit) {}
    }
}
