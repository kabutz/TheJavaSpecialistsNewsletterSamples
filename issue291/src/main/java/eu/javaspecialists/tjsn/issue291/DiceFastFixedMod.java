package eu.javaspecialists.tjsn.issue291;

import java.util.stream.*;

public class DiceFastFixedMod extends DiceFast {
    private static final int[] rolls = IntStream.iterate(0,
            i -> (i + 1) % 6).limit(65536).toArray();
    private static final int MASK = rolls.length - 1;

    protected int mod(int x, int ignored) {
        return rolls[x & MASK];
    }
}
