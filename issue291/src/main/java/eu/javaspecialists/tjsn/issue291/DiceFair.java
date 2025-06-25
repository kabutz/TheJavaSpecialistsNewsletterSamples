package eu.javaspecialists.tjsn.issue291;

import java.util.*;
import java.util.concurrent.*;

public class DiceFair implements Dice {
    private final Random random;

    public DiceFair() {
        this(ThreadLocalRandom.current());
    }

    public DiceFair(Random random) {
        this.random = random;
    }

    public int roll() {
        return random.nextInt(1, 7);
    }
}
