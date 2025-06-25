package eu.javaspecialists.tjsn.issue058;

import java.io.*;
import java.net.*;

public class MonitoringServerSocket extends ServerSocket {
    public MonitoringServerSocket(int port) throws IOException {
        super(port);
    }

    public Socket accept() throws IOException {
        Socket socket = new MonitoringSocket();
        implAccept(socket);
        return socket;
    }
}
