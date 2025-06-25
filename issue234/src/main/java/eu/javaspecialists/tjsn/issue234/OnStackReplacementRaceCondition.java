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

package eu.javaspecialists.tjsn.issue234;

import java.util.*;

// based on https://stackoverflow.com/questions/32994608/java-8-odd-timing-memory-issue
// java-8-odd-timing-memory-issue
public class OnStackReplacementRaceCondition {
    private static volatile boolean running = true;

    public static void main(String... args) {
        new Timer(true).schedule(new TimerTask() {
            public void run() {
                running = false;
            }
        }, 1000);
        double array[] = new double[1];
        Random r = new Random();
        while (running) {
            double someArray[] = new double[1];
            double someArray2[] = new double[2];

            for (int i = 0; i < someArray2.length; i++) {
                someArray2[i] = r.nextDouble();
            }

            // for whatever reason, using r.nextDouble() here doesn't
            // seem to show the problem, but the # you use doesn't seem
            // to matter either...

            someArray[0] = .45;

            array[0] = 1.0;

            // can use any double here instead of r.nextDouble()
            // or some double arithmetic instead of the new Double
            new Double(r.nextDouble());

            double actual;
            if ((actual = array[0]) != 1.0) {
                System.err.println(
                        "claims array[0] != 1.0....array[0] = " + array[0] +
                                ", was " + actual);

                if (array[0] != 1.0) {
                    System.err.println(
                            "claims array[0] still != 1.0...array[0] = " +
                                    array[0]);
                } else {
                    System.err.println(
                            "claims array[0] now == 1.0...array[0] = " +
                                    array[0]);
                }

                System.exit(1);
            } else if (r.nextBoolean()) {
                array = new double[1];
            }
        }
        System.out.println("All good");
    }
}
