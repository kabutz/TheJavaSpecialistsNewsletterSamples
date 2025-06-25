package eu.javaspecialists.tjsn.issue023;

import java.io.*;
import java.net.*;

public class Server {
    public static final int PORT = 4444;

    public Server(int port) throws IOException {
        ServerSocket ss = new ServerSocket(port);
        while (true) {
            new ServerThread(ss.accept());
        }
    }

    private class ServerThread extends Thread {
        private final Socket socket;

        public ServerThread(Socket socket) {
            this.socket = socket;
            start();
        }

        public void run() {
            try {
                ObjectOutputStream out = new ObjectOutputStream(
                        socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(
                        socket.getInputStream());
                while (true) {
                    in.readObject();
                    out.writeObject(new String("test"));
                    out.flush();
                    out.reset();
                }
            } catch (Throwable t) {
                System.out.println("Caught " + t + " - closing thread");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Server(PORT);
    }
}