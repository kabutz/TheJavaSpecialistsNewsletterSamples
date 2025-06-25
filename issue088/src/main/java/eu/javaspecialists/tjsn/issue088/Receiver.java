package eu.javaspecialists.tjsn.issue088;

import java.io.*;
import java.net.*;

public class Receiver {
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(7000);
        Socket socket = ss.accept();
        ObjectInputStream ois = new ObjectInputStream(
                socket.getInputStream());
        int count = 0;
        while (true) {
            Person p = (Person) ois.readObject();
            if (count++ % 1000 == 0) {
                System.out.println(p);
            }
        }
    }
}