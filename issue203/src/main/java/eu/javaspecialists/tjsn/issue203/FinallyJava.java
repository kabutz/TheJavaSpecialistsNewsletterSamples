package eu.javaspecialists.tjsn.issue203;

import java.util.concurrent.*;

public class FinallyJava {
    public void foo() {
        try {
            if (ThreadLocalRandom.current().nextBoolean()) {
                System.out.println("First random true");
                return;
            }
            if (ThreadLocalRandom.current().nextBoolean()) {
                System.out.println("Second random true");
                return;
            }
            System.out.println("Both randoms false");
        } finally {
            System.out.println("Done");
        }
    }

    public static void main(String... args) {
        new FinallyJava().foo();
    }
}
