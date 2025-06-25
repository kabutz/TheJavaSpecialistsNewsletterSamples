package eu.javaspecialists.tjsn.issue003;

import java.io.*;

public class TeeDemo1 {
    // ... the main class that starts up the application server
    public static void main(String[] args) throws IOException {
        PrintStream out1 = System.out;
        OutputStream out2 = new BufferedOutputStream(
                new FileOutputStream("LOG"));
        TeeOutputStream newOut = new TeeOutputStream(out1, out2);
        System.setOut(new PrintStream(newOut, true));
        out1 = System.err;
        newOut = new TeeOutputStream(out1, out2);
        System.setErr(new PrintStream(newOut, true));
        // ... some more code...
    }
}