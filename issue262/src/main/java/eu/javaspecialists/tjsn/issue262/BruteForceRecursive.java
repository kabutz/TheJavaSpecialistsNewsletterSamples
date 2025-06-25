package eu.javaspecialists.tjsn.issue262;

import java.util.*;

public class BruteForceRecursive extends BruteForceBase {
    public static void main(String... args) {
        byte[] is = new byte[7];
        Arrays.fill(is, (byte) '!');
        check(0, 0, is, 0);
    }

    static void check(int depth, int h, byte[] is, int target) {
        if (depth == 7) {
            if (h == target) {
                System.out.println(new String(is));
            }
            return;
        }

        for (byte i : alphabet) {
            is[depth] = i;
            check(depth + 1, h * 31 + i, is, target);
        }
    }
}
