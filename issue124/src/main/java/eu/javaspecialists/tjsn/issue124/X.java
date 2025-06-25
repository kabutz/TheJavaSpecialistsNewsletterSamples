package eu.javaspecialists.tjsn.issue124;

import java.util.*;

public class X {
    private final byte[] byteValue = new byte[16];

    X() {
        new Random(0).nextBytes(byteValue);
    }

    public byte[] testClone() {
        return byteValue.clone();
    }

    public byte[] testNewAndCopy() {
        byte[] b = new byte[byteValue.length];
        System.arraycopy(byteValue, 0, b, 0, byteValue.length);
        return b;
    }

    public static void main(String... args) {
        doTest();
        doTest();
    }

    private static void doTest() {
        X x = new X();
        int m = 50000000;

        long t0 = System.currentTimeMillis();
        for (int i = 0; i < m; i++) {
            x.testClone();
        }
        long t1 = System.currentTimeMillis();

        System.out.println("clone(): " + (t1 - t0));

        t0 = System.currentTimeMillis();
        for (int i = 0; i < m; i++) {
            x.testNewAndCopy();
        }
        t1 = System.currentTimeMillis();

        System.out.println("arraycopy(): " + (t1 - t0));
    }
}
