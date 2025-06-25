package eu.javaspecialists.tjsn.issue230;

import java.util.*;

public class LargeString {
    public static void main(String... args) {
        char[] largeText = new char[10 * 1000 * 1000];
        Arrays.fill(largeText, 'A');
        String superString = new String(largeText);

        long bytes = Memory.threadAllocatedBytes();
        String[] subStrings = new String[largeText.length / 1000];
        for (int i = 0; i < subStrings.length; i++) {
            subStrings[i] = superString.substring(
                    i * 1000, i * 1000 + 1000);
        }
        bytes = Memory.threadAllocatedBytes() - bytes;
        System.out.printf("%,d%n", bytes);
    }
}
