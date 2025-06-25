package eu.javaspecialists.tjsn.issue190;

import java.io.*;

public class AutoClosingFiles {
    public static void main(String... args) throws IOException {
        try (
                PrintStream out = new PrintStream(
                        new BufferedOutputStream(
                                new FileOutputStream("file.txt")))
        ) {
            out.print("This would normally not be written!");
        }
    }
}
