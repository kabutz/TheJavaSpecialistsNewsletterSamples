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

package eu.javaspecialists.tjsn.issue262;

import java.util.*;

public class StringDOS {
    private static final String[] zeroHashCodes = {
            "ARbyguv", "ARbygvW", "ARbyhVv", "ARbyhWW",
            "ARbzHuv", "ARbzHvW", "ARbzIVv", "ARbzIWW",
            "ARcZguv", "ARcZgvW", "ARcZhVv", "ARcZhWW",
            "ASCyguv", "ASCygvW", "ASCyhVv", "ASCyhWW",
            "ASCzHuv", "ASCzHvW", "ASCzIVv", "ASCzIWW",
            "ASDZguv", "ASDZgvW", "ASDZhVv", "ASDZhWW",
    };

    public static void main(String... args) {
        for (int i = 5; i < 24; i++) {
            System.out.println("Testing with size " + (1 << i));
            test(1 << i);
        }
    }

    private static void test(int size) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        long time = System.nanoTime();
        try {
            for (int i = 0; i < size; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 1, index = 0; j <= i; j <<= 1, index++) {
                    if ((i & j) != 0)
                        sb.append(zeroHashCodes[index % zeroHashCodes.length]);
                }
                map.put(sb.toString(), i);
            }
        } finally {
            time = System.nanoTime() - time;
            System.out.printf("creating time = %dms%n", (time / 1000000));
        }

        System.out.println("map.size() = " + map.size());
        for (String s : map.keySet()) {
            if (s.hashCode() != 0)
                throw new AssertionError("hashCode() of " + s + " is not 0");
        }
        System.out.println("All hashCode() were 0");
        String notInMap = zeroHashCodes[1] + zeroHashCodes[0];
        for (int repeats = 0; repeats < 10; repeats++) {
            testLookup(map, notInMap);
        }
    }

    private static void testLookup(Map<String, Integer> map, String notInMap) {
        long time = System.nanoTime();
        try {
            for (int i = 0; i < 1000 * 1000; i++) {
                map.get(notInMap);
            }
        } finally {
            time = System.nanoTime() - time;
            System.out.printf("time = %dms%n", (time / 1000000));
        }
    }
}
