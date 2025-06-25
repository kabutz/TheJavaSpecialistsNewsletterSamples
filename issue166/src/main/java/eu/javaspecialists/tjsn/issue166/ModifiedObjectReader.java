package eu.javaspecialists.tjsn.issue166;

import java.io.*;

public class ModifiedObjectReader {
    public static void main(String... args) throws Exception {
        ObjectInputStream in = new ObjectInputStream(
                new BufferedInputStream(
                        new FileInputStream("smalldata.bin")
                )
        );
        byte[] data;
        while ((data = (byte[]) in.readObject()) != null) {
            System.out.println(data[0]);
        }
        in.close();
    }
}
