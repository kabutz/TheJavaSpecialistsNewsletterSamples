package eu.javaspecialists.tjsn.issue058;

import sun.rmi.transport.proxy.*;

import java.io.*;
import java.net.*;
import java.rmi.server.*;

public class MonitoringMasterSocketFactory
        extends RMIMasterSocketFactory {
    public MonitoringMasterSocketFactory() {
        initialFactory = new RMISocketFactory() {
            public Socket createSocket(String host, int port)
                    throws IOException {
                return new MonitoringSocket(host, port);
            }

            public ServerSocket createServerSocket(int port)
                    throws IOException {
                return new MonitoringServerSocket(port);
            }
        };
    }
}
