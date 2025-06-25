package eu.javaspecialists.tjsn.issue291;

import java.util.concurrent.*;

public class DiceFast implements Dice {
    private int x = ThreadLocalRandom.current().nextInt();
    private int y = ThreadLocalRandom.current().nextInt();
    private int z = ThreadLocalRandom.current().nextInt();
    private int w = ThreadLocalRandom.current().nextInt();

    // https://www.jstatsoft.org/article/view/v008i14
    public int roll() {
        int tmp = (x ^ (x << 15));
        x = y;
        y = z;
        z = w;
        w = (w ^ (w >> 21)) ^ (tmp ^ (tmp >> 4));
        return mod(w, 6) + 1;
    }

    protected int mod(int x, int div) {
        int result = x % div;
        if (result < 0) return -result;
        return result;
    }
}
