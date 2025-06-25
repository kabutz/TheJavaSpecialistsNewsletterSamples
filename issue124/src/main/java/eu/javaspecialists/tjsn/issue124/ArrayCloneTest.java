package eu.javaspecialists.tjsn.issue124;

import java.util.*;

public class ArrayCloneTest implements Runnable {
    private final byte[] byteValue;

    public ArrayCloneTest(int length) {
        byteValue = new byte[length];
        // always the same set of bytes...
        new Random(0).nextBytes(byteValue);
    }

    public void run() {
        byte[] result = byteValue.clone();
    }
}