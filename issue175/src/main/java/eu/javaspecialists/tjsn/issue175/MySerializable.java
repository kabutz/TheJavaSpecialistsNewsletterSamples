package eu.javaspecialists.tjsn.issue175;

import java.io.*;

public class MySerializable extends NotSerializable
        implements Serializable {
    public MySerializable() {
        System.out.println("MySerializable constructor called");
    }
}
