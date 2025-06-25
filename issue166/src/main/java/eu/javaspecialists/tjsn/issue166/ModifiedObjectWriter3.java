package eu.javaspecialists.tjsn.issue166;

import java.io.*;
import java.util.*;

public class ModifiedObjectWriter3 {
    public static void main(String... args) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream("verylargedata.bin")
                )
        );
        byte[] data = new byte[10 * 1024 * 1024];
        for (int i = -128; i < 128; i++) {
            Arrays.fill(data, (byte) i);
            out.writeObject(data);
            out.reset();
        }
        out.writeObject(null);
        out.close();
    }
}
