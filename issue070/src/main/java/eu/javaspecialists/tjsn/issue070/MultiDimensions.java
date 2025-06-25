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

package eu.javaspecialists.tjsn.issue070;

public class MultiDimensions {
    private static final int NUM_BINS = 1000;
    private static final int ITERATIONS = 100000;

    public static void main(String[] args) {
        testMultiArray();
        testMultiArray2();
        testSingleArray();
    }

    private static void testMultiArray() {
        long time = -System.currentTimeMillis();
        // just making sure that the number of operations is equal
        int ops = 0;

        for (int repeat = 0; repeat < ITERATIONS; repeat++) {
            int[][] aTwoDim = new int[NUM_BINS][4];
            for (int i = 0; i < aTwoDim.length; i++) {
                for (int j = 0; j < aTwoDim[i].length; j++) {
                    ops++;
                    aTwoDim[i][j] = j;
                }
            }
        }

        time += System.currentTimeMillis();
        System.out.println(ops);
        System.out.println("Time Elapsed for [][4] - " + time);
    }

    private static void testMultiArray2() {
        long time = -System.currentTimeMillis();
        int ops = 0;

        for (int repeat = 0; repeat < ITERATIONS; repeat++) {
            int[][] aTwoDim = new int[4][NUM_BINS];
            for (int i = 0; i < aTwoDim.length; i++) {
                for (int j = 0; j < aTwoDim[i].length; j++) {
                    ops++;
                    aTwoDim[i][j] = j;
                }
            }
        }

        time += System.currentTimeMillis();
        System.out.println(ops);
        System.out.println("Time Elapsed for [4][] - " + time);
    }

    private static void testSingleArray() {
        long time = -System.currentTimeMillis();
        int ops = 0;

        for (int repeat = 0; repeat < ITERATIONS; repeat++) {
            int[] aOneDim = new int[NUM_BINS * 4];
            for (int i = 0; i < aOneDim.length / 4; i++) {
                for (int j = 0; j < 4; j++) {
                    ops++;
                    aOneDim[i * 4 + j] = j;
                }
            }
        }

        time += System.currentTimeMillis();
        System.out.println(ops);
        System.out.println("Time Elapsed for [] - " + time);
    }
}
