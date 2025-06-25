package eu.javaspecialists.tjsn.issue169;

import eu.javaspecialists.tjsn.issue169.performance.*;

import java.io.*;
import java.net.*;

public class ClientTest {
    public static void main(String args[]) throws Exception {
        // Comment out this line if you want to use AspectJ
        SocketMonitoringSystem.initForDelegator();

        BasicSocketMonitor monitor = new BasicSocketMonitor();
        SocketMonitoringSystem.getInstance().add(monitor);

        Socket socket = new Socket("localhost", 8080);
        OutputStream out1 = socket.getOutputStream();
        PrintWriter out = new PrintWriter(out1, true);
        InputStream in1 = socket.getInputStream();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(in1)
        );
        for (int i = 0; i < 13; i++) {
            out.println("Test");
            System.out.println(in.readLine());
            System.out.println(in.readLine());
        }

        monitor.dump();
    }
}
