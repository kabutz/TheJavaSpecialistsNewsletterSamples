package eu.javaspecialists.tjsn.issue233;

import java.io.*;

public class MultiplePersonalities {
    public Runnable create() {
        return (Runnable & Serializable) () ->
                System.out.println("Hello World");
    }
}
