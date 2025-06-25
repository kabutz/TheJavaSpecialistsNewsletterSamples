package eu.javaspecialists.tjsn.issue023;

import java.io.*;
import java.net.*;

public class MultiClient {
    public MultiClient(int port) throws Exception {
        long time = -System.currentTimeMillis();
        Socket[] sockets = new Socket[3500];
        ObjectOutputStream[] outs =
                new ObjectOutputStream[sockets.length];
        ObjectInputStream[] ins =
                new ObjectInputStream[sockets.length];
        for (int i = 0; i < sockets.length; i++) {
            sockets[i] = new Socket("localhost", port);
            outs[i] = new ObjectOutputStream(
                    sockets[i].getOutputStream());
            ins[i] = new ObjectInputStream(
                    sockets[i].getInputStream());
        }
        System.out.println("Constructed all sockets");
        for (int j = 0; j < 32; j++) {
            long iterationTime = -System.currentTimeMillis();
            for (int i = 0; i < sockets.length; i++) {
                outs[i].writeObject(new Integer(i));
                outs[i].flush();
                outs[i].reset();
            }
            System.out.println(j + ": Written to all sockets");
            for (int i = 0; i < sockets.length; i++) {
                ins[i].readObject();
            }
            System.out.println(j + ": Read from all sockets");
            iterationTime += System.currentTimeMillis();
            System.out.println(j + ": Iteration took " +
                    iterationTime + "ms");
        }
        time += System.currentTimeMillis();
        System.out.println("Writing to " + sockets.length +
                " sockets 32 times took " + time + "ms");
    }

    public static void main(String[] args) throws Exception {
        new MultiClient(Server.PORT);
    }
}