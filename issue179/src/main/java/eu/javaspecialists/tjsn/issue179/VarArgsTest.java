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

package eu.javaspecialists.tjsn.issue179;

public class VarArgsTest {
    public static void main(String... args) {
        long time = System.currentTimeMillis();
        long grandTotal = 0;
        for (int i = 0; i < 100000; i++) {
            grandTotal += test();
        }
        time = System.currentTimeMillis() - time;
        System.out.println("time = " + time + "ms");
        System.out.println("grandTotal = " + grandTotal);
    }

    private static long test() {
        long total = 0;
        for (int i = 0; i < 10000; i++) {
            total += test(
                    i, 1, 2, 3, 4, 5, 6, 7, 8, 9,
                    i, 1, 2, 3, 4, 5, 6, 7, 8, 9,
                    i, 1, 2, 3, 4, 5, 6, 7, 8, 9,
                    i, 1, 2, 3, 4, 5, 6, 7, 8, 9,
                    i, 1, 2, 3, 4, 5, 6, 7, 8, 9,
                    i, 1, 2, 3, 4, 5, 6, 7, 8, 9,
                    i, 1, 2, 3
            );
        }
        return total;
    }

    public static int test(int... args) {
        return args[0] + args.length;
    }
}
