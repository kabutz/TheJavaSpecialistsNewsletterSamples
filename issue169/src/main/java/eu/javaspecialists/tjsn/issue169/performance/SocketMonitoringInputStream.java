package eu.javaspecialists.tjsn.issue169.performance;


import java.io.*;
import java.net.*;

public class SocketMonitoringInputStream extends InputStream {
    private final Socket socket;
    private final InputStream in;

    public SocketMonitoringInputStream(Socket socket,
                                       InputStream in)
            throws IOException {
        this.socket = socket;
        this.in = in;
    }

    public int read() throws IOException {
        int result = in.read();
        if (result != -1) {
            SocketMonitoringSystem.getInstance().read(socket, result);
        }
        return result;
    }

    public int read(byte[] b, int off, int len)
            throws IOException {
        int length = in.read(b, off, len);
        if (length != -1) {
            SocketMonitoringSystem.getInstance().
                    read(socket, b, off, length);
        }
        return length;
    }
}
