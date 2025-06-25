package eu.javaspecialists.tjsn.issue276;

import java.io.*;

public class PersonWriteTest {
    public static void main(String... args)
            throws IOException {
        try (var out =
                     new ObjectOutputStream(
                             new FileOutputStream("persons.bin"))) {
            out.writeObject(new PersonRecord("Heinz", "Kabutz"));
            out.writeObject(new PersonClass("Heinz", "Sommerfeld"));
            out.writeObject(null);
        }
    }
}
