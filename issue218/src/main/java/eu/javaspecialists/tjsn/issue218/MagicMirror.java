package eu.javaspecialists.tjsn.issue218;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class MagicMirror {
    private static final ThreadLocalRandom tlr =
            ThreadLocalRandom.current();

    public boolean amIPretty() {
        return tlr.nextBoolean();
    }

    public static void main(String... args) {
        final AtomicBoolean vanity = new AtomicBoolean(true);
        while (vanity.get()) {
            new Thread(new Runnable() {
                public void run() {
                    MagicMirror mirrorOnTheWall = new MagicMirror();
                    boolean beauty = mirrorOnTheWall.amIPretty();
                    if (!beauty) vanity.set(false);
                }
            }).start();
        }
        System.out.println("Oh no, now I am depressed!");
    }
}
