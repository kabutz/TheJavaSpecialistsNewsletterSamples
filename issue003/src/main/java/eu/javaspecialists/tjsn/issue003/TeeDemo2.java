package eu.javaspecialists.tjsn.issue003;

import java.io.*;

public class TeeDemo2 {
    public static void main(String[] args) throws IOException {
        OutputStream log = new BufferedOutputStream(
                new FileOutputStream("LOG"));
        System.setOut(
                new PrintStream(new TeeOutputStream(System.out,log),true));
        System.setErr(
                new PrintStream(new TeeOutputStream(System.err,log),true));
        // ... some more code...
    }
}