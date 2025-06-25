package eu.javaspecialists.tjsn.issue056;

import java.io.*;
import java.net.*;

public class BlockedOnSocketIO {
    public static void main(String[] args)
            throws IOException, InterruptedException {
        ServerSocket ss = new ServerSocket(4444);
        Socket socket = new Socket("localhost", 4444);
        System.out.println("Made socket, now reading from socket");
        Thread t = new BlockedOnIO(socket.getInputStream());
        t.start();
        Thread.sleep(5000);
        t.interrupt();
    }
}
