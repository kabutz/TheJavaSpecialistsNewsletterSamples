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

package eu.javaspecialists.tjsn.issue090;

import java.util.*;

public class NewForPerformance {
    public static void main(String... args) {
        // let's look at the performance difference of for construct
        Collection<Integer> numbers = new ArrayList<Integer>(10000);
        for (int i = 0; i < 10000; i++) {
            // I can add an "int" to a collection of Integer, thanks
            // to the autoboxing construct shamelessly copied from C#
            numbers.add((int) Math.random());
        }
        oldFor(numbers);
        newFor(numbers);
    }

    private static void oldFor(final Collection<Integer> numbers) {
        measureIterations("oldFor", new Runnable() {
            public void run() {
                for (Iterator<Integer> it = numbers.iterator(); it.hasNext(); ) {
                    Integer i = it.next();
                }
            }
        });
    }

    private static void newFor(final Collection<Integer> numbers) {
        measureIterations("newFor", new Runnable() {
            public void run() {
                for (Integer i : numbers) ;
            }
        });
    }

    private static void measureIterations(String method, Runnable r) {
        long start = System.currentTimeMillis();
        int iterations = 0;
        while (System.currentTimeMillis() - start <= 2000) {
            r.run();
            iterations++;
        }
        System.out.println(method + ": " + iterations + " in " +
                (System.currentTimeMillis() - start) + "ms");
    }
}
