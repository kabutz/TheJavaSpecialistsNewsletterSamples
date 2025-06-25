package eu.javaspecialists.tjsn.issue169;

import eu.javaspecialists.tjsn.issue169.performance.*;
import eu.javaspecialists.tjsn.issue169.stats.*;

import java.io.*;
import java.net.*;

public class SocketServerTest {
    public static void main(String[] args) throws IOException {
        // Comment out these lines if you want to use AspectJ
        SocketMonitoringSystem.initForDelegator();
        SocketMonitoringSystem.getInstance().add(StatsManager.getSocketStats());

        ServerSocket ss = new ServerSocket(8080);

        while (true) {
            Socket socket = ss.accept();
            System.out.println("socket = " + socket);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            int data;
            while ((data = in.read()) != -1) {
                // write the data twice
                out.write(data);
                out.write(data);
                out.flush();
            }
        }
    }
}
