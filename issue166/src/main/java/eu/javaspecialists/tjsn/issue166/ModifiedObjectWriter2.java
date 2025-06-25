package eu.javaspecialists.tjsn.issue166;

import java.io.*;
import java.util.*;

public class ModifiedObjectWriter2 {
    public static void main(String... args) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream("verylargedata.bin")
                )
        );
        for (int i = -128; i < 128; i++) {
            byte[] data = new byte[10 * 1024 * 1024];
            Arrays.fill(data, (byte) i);
            out.writeObject(data);
        }
        out.writeObject(null);
        out.close();
    }
}
