package eu.javaspecialists.tjsn.issue190;

import java.io.*;

public class AutoClosingFiles2 {
    public static void main(String... args) throws IOException {
        try (
                FileOutputStream fout = new FileOutputStream("file.txt");
                BufferedOutputStream bout = new BufferedOutputStream(fout);
                PrintStream out = new PrintStream(bout)
        ) {
            out.print("This would normally not be written!");
        }
    }
}
