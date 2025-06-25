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

package eu.javaspecialists.tjsn.issue191;

import java.util.*;

public class Quiz44 {
    private static final int NB_VALUES = 10000000;
    private static final int MAX_VALUE = 1000;
    private static final int NB_RUNS = 10;

    public static void main(String... args) {
        Integer[] boxedValues = new Integer[NB_VALUES];
        int[] values = initValues();

        System.out.println("Benchmarking...");
        for (int run = 1; run <= NB_RUNS; run++) {
            long t1 = System.currentTimeMillis();
            for (int i = 0; i < NB_VALUES; i++) {
                boxedValues[i] = values[i];
            }
            long t2 = System.currentTimeMillis();
            System.out.printf("Run %2d : %4dms%n", run, t2 - t1);
        }
    }

    /**
     * Nothing important here, just values init.
     */
    private static int[] initValues() {
        System.out.println("Generating values...");
        int[] values = new int[NB_VALUES];
        Random random = new Random();
        for (int i = 0; i < values.length; i++) {
            values[i] = random.nextInt(MAX_VALUE);
        }
        return values;
    }
}
