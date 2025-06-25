package eu.javaspecialists.tjsn.issue303;

import java.util.*;

public class ZeroHashFun {
    private static final String[] zeroHashCodes = {
            "ARbyguv", "ARbygvW", "ARbyhVv", "ARbyhWW", "ARbzHuv",
            "ARbzHvW", "ARbzIVv", "ARbzIWW", "ARcZguv", "ARcZgvW",
            "ARcZhVv", "ARcZhWW", "ASCyguv", "ASCygvW", "ASCyhVv",
            "ASCyhWW", "ASCzHuv", "ASCzHvW", "ASCzIVv", "ASCzIWW",
            "ASDZguv", "ASDZgvW", "ASDZhVv", "ASDZhWW",};

    public static void main(String... args) {
        for (int i = 16; i <= 24; i++) {
            System.out.println("Testing with size " + (1 << i));
            test(1 << i);
        }
    }

    private static void test(int size) {
        HashSet<String> set = HashSet.newHashSet(size);
        for (int i = 0; i < size - 1; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 1, index = 0; j <= i; j <<= 1, index++) {
                if ((i & j) != 0)
                    sb.append(zeroHashCodes[index % zeroHashCodes.length]);
            }
            set.add(sb.toString());
        }
        set.add(null); // also adding a null key
        System.out.println("set.size() = " + set.size());

        System.out.println("Searching for key not in the set");
        String notInMap = zeroHashCodes[1] + zeroHashCodes[0];
        for (int repeats = 0; repeats < 10; repeats++) {
            testLookup(set, notInMap);
        }
        System.out.println("Searching for null key");
        for (int repeats = 0; repeats < 10; repeats++) {
            testLookup(set, null);
        }
    }

    private static void testLookup(Set<String> set, String key) {
        long time = System.nanoTime();
        try {
            set.contains(key);
        } finally {
            time = System.nanoTime() - time;
            System.out.printf("time = %dms%n", (time / 1000000));
        }
    }
}
