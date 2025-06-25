package eu.javaspecialists.tjsn.issue023;

import java.io.*;
import java.net.*;
import java.util.*;

public class SocketWheel {
    // the list contains SocketBuckets
    private final List sockets = new LinkedList();
    // we don't want to block a new connection while we are busy
    // serving the existing ones
    private final List newSockets = new LinkedList();

    public SocketWheel() {
        new ServerThread();
    }

    public void addSocket(Socket socket) throws IOException {
        synchronized (newSockets) {
            newSockets.add(new SocketBucket(socket));
            newSockets.notify();
        }
    }

    private class SocketBucket {
        public final Socket socket;
        public final ObjectOutputStream out;
        public final ObjectInputStream in;

        public SocketBucket(Socket socket) throws IOException {
            this.socket = socket;
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            socket.setSoTimeout(1);  // VERY short timeout
        }
    }

    private class ServerThread extends Thread {
        public ServerThread() {
            super("ServerThread");
            start();
        }

        public void run() {
            long dreamTime = 10;
            boolean foundSomething;
            while (true) {
                try {
                    synchronized (newSockets) {
                        sockets.addAll(newSockets);
                        newSockets.clear();
                    }
                    foundSomething = false;
                    Iterator it = sockets.iterator();
                    while (it.hasNext()) {
                        SocketBucket bucket = (SocketBucket) it.next();
                        try {
                            bucket.in.readObject();
                            foundSomething = true;
                            bucket.out.writeObject(new String("test"));
                            bucket.out.flush();
                            bucket.out.reset();
                        } catch (InterruptedIOException ex) {
                            // just skip this socket
                        } catch (IOException ex) {
                            it.remove();
                        }
                    }
                    if (foundSomething) {
                        dreamTime = 6;
                    } else {
                        if (dreamTime < 1000)
                            dreamTime *= 1.5;
                        else dreamTime = 1000;
                        synchronized (newSockets) {
                            // only sleep if we didn't find anything
                            newSockets.wait(dreamTime);
                        }
                    }
                } catch (Throwable t) {
                    System.out.println("Caught " + t + " - remove socket");
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        SocketWheel wheel = new SocketWheel();
        ServerSocket ss = new ServerSocket(Server.PORT);
        while (true) {
            Socket socket = ss.accept();
            wheel.addSocket(socket);
        }
    }
}