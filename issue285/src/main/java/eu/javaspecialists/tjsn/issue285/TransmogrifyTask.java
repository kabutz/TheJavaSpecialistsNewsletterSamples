package eu.javaspecialists.tjsn.issue285;

import java.io.*;
import java.net.*;

class TransmogrifyTask implements Runnable {
    private final Socket socket;

    public TransmogrifyTask(Socket socket) throws IOException {
        this.socket = socket;
    }

    public void run() {
        try (socket;
             InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream()
        ) {
            while (true) {
                int val = in.read();
                if (Character.isLetter(val))
                    val ^= ' '; // change case of all letters
                out.write(val);
            }
        } catch (IOException e) {
            // connection closed
        }
    }
}
