package eu.javaspecialists.tjsn.issue317;

import java.io.*;
import java.lang.management.*;

public class DiagnosticVsExplicitGC {
    public static void main(String... args) throws IOException {
        // turn on verbose GC
        ManagementFactory.getMemoryMXBean().setVerbose(true);

        // create some objects
        for (int i = 0; i < 1000; i++) {
            byte[] object = new byte[100_000];
        }

        // wait for input
        BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in)
        );
        System.out.println("Press ENTER to invoke explicit GC");
        in.readLine();
        System.gc();
        System.out.println("Press ENTER to exit program");
        in.readLine();
    }
}
