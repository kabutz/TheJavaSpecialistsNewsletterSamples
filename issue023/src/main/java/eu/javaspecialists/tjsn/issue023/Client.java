package eu.javaspecialists.tjsn.issue023;

import java.io.*;
import java.net.*;

public class Client {
    public Client(int port) throws Exception {
        Socket socket = new Socket("localhost", port);
        ObjectOutputStream out = new ObjectOutputStream(
                socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(
                socket.getInputStream());
        for (int i = 0; i < 10; i++) {
            out.writeObject(new Integer(i));
            out.flush();
            out.reset();
            System.out.println(in.readObject());
            Thread.sleep(1000);
        }
    }

    public static void main(String[] args) throws Exception {
        new Client(Server.PORT);
    }
}