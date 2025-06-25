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

public class OptimizingWithOSR {
    public static void main(String... args) {
        long time = System.currentTimeMillis();
        double d = testOnce();
        System.out.println("d = " + d);
        time = System.currentTimeMillis() - time;
        System.out.println("time = " + time);
    }

    private static double testOnce() {
        double d = 0;
        for (int i = 0; i < 1_000_000_000; i++) {
            d += 0;
        }
        return d;
    }
}
