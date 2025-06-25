package eu.javaspecialists.tjsn.issue088;

import java.io.*;
import java.net.*;

public class Sender3 {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        Socket s = new Socket("localhost", 7000);
        ObjectOutputStream oos = new ObjectOutputStream(
                s.getOutputStream());
        for (int age = 0; age < 1500 * 1000; age++) {
            oos.writeObject(new Person("Heinz", "Kabutz", age));
            oos.reset();
        }
        long end = System.currentTimeMillis();
        System.out.println("That took " + (end - start) + "ms");
    }
}
