package eu.javaspecialists.tjsn.issue259;

import java.io.*;

public class Java9TryWithResource {
    public static void main(String... args) throws IOException {
        printClose(new FileInputStream("Java9TryWithResource.java"));
    }

    private static void printClose(InputStream in)
            throws IOException {
        try (in; // <-- Compiler error prior to Java 9
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(in)
             )) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
