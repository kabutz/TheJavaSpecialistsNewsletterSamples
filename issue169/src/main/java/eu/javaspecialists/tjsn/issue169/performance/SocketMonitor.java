package eu.javaspecialists.tjsn.issue169.performance;

import java.io.*;
import java.net.*;

public interface SocketMonitor {
    void write(Socket socket, int data) throws IOException;

    void write(Socket socket, byte[] data, int off, int len)
            throws IOException;

    void read(Socket socket, int data) throws IOException;

    void read(Socket socket, byte[] data, int off, int len)
            throws IOException;
}
