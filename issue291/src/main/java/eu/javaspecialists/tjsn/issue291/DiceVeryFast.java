package eu.javaspecialists.tjsn.issue291;

import java.lang.invoke.*;
import java.util.concurrent.*;

public class DiceVeryFast implements Dice {
    // fits in L2 cache at 512k
    private static final byte[] rolls = new byte[512 * 1024];

    private int pos = ThreadLocalRandom.current()
            .nextInt(0, rolls.length);
    private static final int MASK = rolls.length - 1;

    public int roll() {
        return (byte) ROLLS.getOpaque(rolls, (pos++ & MASK));
    }

    private static final VarHandle ROLLS =
            MethodHandles.arrayElementVarHandle(byte[].class);

    static {
        fillWithRandom();
        Thread shuffler = new Thread(() -> {
            while (true) {
                fillWithRandom();
            }
        }, "DiceVeryFast-Shuffler");
        shuffler.setDaemon(true);
        shuffler.start();
    }

    private static void fillWithRandom() {
        int size = rolls.length;
        var rnd = ThreadLocalRandom.current();
        for (int i = size - 1; i >= 0; i--) {
            ROLLS.setOpaque(rolls, i, (byte) rnd.nextInt(1, 7));
        }
    }
}
