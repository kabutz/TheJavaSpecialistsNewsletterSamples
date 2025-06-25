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

package eu.javaspecialists.tjsn.issue110;

/**
 * Both sort functions iterate through the array to see whether
 * it already is sorted.  If it is, they return the original
 * array, otherwise they construct a new array, copy the old
 * values over sort that and return it.
 */
public class LabeledStatementBreakTest {
    /**
     * The approach I often see used for this type of problem.
     */
    public static int[] sortWithoutBreak(int[] vals) {
        // first check if the array is sorted already
        boolean sorted = true;
        for (int j = 1; j < vals.length && sorted; j++) {
            sorted = vals[j - 1] <= vals[j];
        }
        if (sorted) return vals;

        int[] sortedArray = new int[vals.length];
        System.arraycopy(vals, 0, sortedArray, 0, vals.length);
        java.util.Arrays.sort(sortedArray);
        return sortedArray;
    }

    /**
     * Using a labeled statement combined with a break to jump
     * around the method.
     */
    public static int[] sortWithBreak(int[] vals) {
        // first check if the array is sorted already
        scan:
        {
            for (int j = 1; j < vals.length; j++) {
                if (vals[j - 1] > vals[j]) break scan;
            }
            // sorted already
            return vals;
        }
        int[] sortedArray = new int[vals.length];
        System.arraycopy(vals, 0, sortedArray, 0, vals.length);
        java.util.Arrays.sort(sortedArray);
        return sortedArray;
    }

    /**
     * This is the approach I normally use to solve this.
     */
    public static int[] sortHeinz(int[] vals) {
        // first check if the array is sorted already
        if (isSorted(vals)) return vals;
        int[] sortedArray = new int[vals.length];
        System.arraycopy(vals, 0, sortedArray, 0, vals.length);
        java.util.Arrays.sort(sortedArray);
        return sortedArray;
    }

    private static boolean isSorted(int[] vals) {
        for (int j = 1; j < vals.length; j++) {
            if (vals[j - 1] > vals[j]) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        int[] vals1 = {1, 4, 5, 2, 3};
        int[] vals2 = {-1, 3, 3, 5, 12, 30};

        // output should always be "true"

        System.out.println(vals1 != sortWithoutBreak(vals1));
        System.out.println(vals2 == sortWithoutBreak(vals2));

        System.out.println(vals1 != sortWithBreak(vals1));
        System.out.println(vals2 == sortWithBreak(vals2));

        System.out.println(vals1 != sortHeinz(vals1));
        System.out.println(vals2 == sortHeinz(vals2));
    }
}
