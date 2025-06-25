package eu.javaspecialists.tjsn.issue235;

import java.util.*;

public class MapBucketFillTest {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;

    public static void main(String... args) throws Exception {
        for (Pixel.HASH_ALGO algo : Pixel.HASH_ALGO.values()) {
            testWith(algo);
        }
    }

    private static void testWith(Pixel.HASH_ALGO algo)
            throws NoSuchFieldException, IllegalAccessException {
        System.out.println("Testing with " + algo);
        Pixel.setAlgo(algo);
        Map<Pixel, Integer> pixels = new HashMap<Pixel, Integer>();
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                pixels.put(new Pixel(x, y), 33);
            }
        }

        System.out.println("Hash clash print: ");
        Map<Integer, Integer> hashClashes =
                MapClashInspector.getHashClashDistribution(pixels);
        printClashes(hashClashes);

        System.out.println("Bucket entry clash print: ");
        Map<Integer, Integer> bucketClashes =
                MapClashInspector.getBucketClashDistribution(pixels);
        printClashes(bucketClashes);

        System.out.println();
    }

    private static void printClashes(
            Map<Integer, Integer> clashes) {
        if (isPerfect(clashes)) {
            System.out.println("Perfect!!!");
        }
        for (Map.Entry<Integer, Integer> e : clashes.entrySet()) {
            System.out.println(e.getKey() + ": " + e.getValue());
        }
    }

    private static boolean isPerfect(
            Map<Integer, Integer> clashes) {
        Integer n = clashes.get(1);
        return n != null && n == WIDTH * HEIGHT;
    }
}
