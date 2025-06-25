package eu.javaspecialists.tjsn.issue291;

import java.util.*;

public class EfiBoard extends Board {
    public EfiBoard() {
        super(
                Map.of(
                        17, 6,
                        48, 25,
                        51, 30,
                        59, 40,
                        64, 18,
                        79, 62,
                        83, 75,
                        89, 68,
                        94, 88),
                Map.of(
                        9, 31,
                        19, 38,
                        21, 42,
                        28, 84,
                        36, 57,
                        52, 67,
                        70, 91,
                        80, 99
                ));
    }
}
