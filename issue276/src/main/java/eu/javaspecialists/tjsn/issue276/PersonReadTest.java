package eu.javaspecialists.tjsn.issue276;

import java.io.*;

public class PersonReadTest {
    public static void main(String... args)
            throws IOException, ClassNotFoundException {
        try (var in = new ObjectInputStream(
                new FileInputStream("persons.bin"))) {
            Person p;
            while ((p = (Person) in.readObject()) != null) {
                System.out.println(p);
            }
        }
    }
}
