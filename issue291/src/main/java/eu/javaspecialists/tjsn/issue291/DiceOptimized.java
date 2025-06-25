package eu.javaspecialists.tjsn.issue291;

import java.util.*;
import java.util.stream.*;

public class DiceOptimized implements Dice {
    private final PrimitiveIterator.OfInt sequence;

    public DiceOptimized(int numberOfSixes, int last) {
        this.sequence = IntStream.concat(
                IntStream.generate(() -> 6).limit(numberOfSixes),
                IntStream.of(last)).iterator();
    }

    public int roll() {
        return sequence.next();
    }
}
