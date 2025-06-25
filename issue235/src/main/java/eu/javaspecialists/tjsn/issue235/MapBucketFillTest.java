/*
 * Copyright 2000-2025 Heinz Max Kabutz
 * All rights reserved.
 *
 * From The Java Specialists' Newsletter (https://www.javaspecialists.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
