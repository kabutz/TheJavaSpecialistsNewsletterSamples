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

package eu.javaspecialists.tjsn.issue066;

public class TryCatchCostTest extends TryCatch {
    public static void main(String[] args) {
        Integer i = new Integer(3);
        Boolean b = new Boolean(true);
        long TEST_DURATION = 2 * 1000;
        boolean res;
        long stop;

        stop = TEST_DURATION + System.currentTimeMillis();
        int test1_i = 0;
        // we do not want to test with every increment, otherwise the
        // call to System.currentTimeMillis() will dwarf the rest of
        // our calls.
        while (((test1_i % 1000) != 0)
                || (System.currentTimeMillis() < stop)) {
            test1_i++;
            res = test1(i);
        }
        System.out.println("test1(i) executed " + test1_i + " times");

        stop = TEST_DURATION + System.currentTimeMillis();
        int test1_b = 0;
        while (((test1_b % 1000) != 0)
                || (System.currentTimeMillis() < stop)) {
            test1_b++;
            res = test1(b);
        }
        System.out.println("test1(b) executed " + test1_b + " times");
        System.out.println("test1(i) was " + (test1_i / test1_b)
                + " times faster");

        stop = TEST_DURATION + System.currentTimeMillis();
        int test2_i = 0;
        while (((test2_i % 1000) != 0)
                || (System.currentTimeMillis() < stop)) {
            test2_i++;
            res = test2(i);
        }
        System.out.println("test2(i) executed " + test2_i + " times");

        stop = TEST_DURATION + System.currentTimeMillis();
        int test2_b = 0;
        while (((test2_b % 1000) != 0)
                || (System.currentTimeMillis() < stop)) {
            test2_b++;
            res = test2(b);
        }
        System.out.println("test2(b) executed " + test2_b + " times");
    }
}
