package eu.javaspecialists.tjsn.issue169.performance;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class SocketMonitoringSystem {
    private static final SocketMonitoringSystem instance =
            new SocketMonitoringSystem();

    public static SocketMonitoringSystem getInstance() {
        return instance;
    }

    /**
     * This should only be called if we want to use the reflection
     * approach (Delegator).  If we use Aspects, we should not call
     * this method.
     */
    public static void initForDelegator() throws IOException {
        SocketImplFactory socketImplFactory =
                new MonitoringSocketFactory();
        Socket.setSocketImplFactory(socketImplFactory);
        ServerSocket.setSocketFactory(socketImplFactory);
    }

    private SocketMonitoringSystem() {
    }

    private final Collection<SocketMonitor> monitors =
            new CopyOnWriteArraySet<SocketMonitor>();

    public void add(SocketMonitor monitor) {
        monitors.add(monitor);
    }

    public void remove(SocketMonitor monitor) {
        monitors.remove(monitor);
    }

    public void write(Socket socket, int data)
            throws IOException {
        for (SocketMonitor monitor : monitors) {
            monitor.write(socket, data);
        }
    }

    public void write(Socket socket,
                      byte[] data, int offset, int length)
            throws IOException {
        for (SocketMonitor monitor : monitors) {
            monitor.write(socket, data, offset, length);
        }
    }

    public void read(Socket socket, int data)
            throws IOException {
        for (SocketMonitor monitor : monitors) {
            monitor.read(socket, data);
        }
    }

    public void read(Socket socket,
                     byte[] data, int offset, int length)
            throws IOException {
        for (SocketMonitor monitor : monitors) {
            monitor.read(socket, data, offset, length);
        }
    }
}
