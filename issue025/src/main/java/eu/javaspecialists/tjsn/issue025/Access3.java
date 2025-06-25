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

package eu.javaspecialists.tjsn.issue025;

public class Access3 {
    public void f() {
        Runnable[] runners = new Runnable[10];
        for (final int[] i = {0}; i[0] < runners.length; i[0]++) {
            runners[i[0]] = new Runnable() {
                private int counter = i[0];

                public void run() {
                    System.out.println(counter);
                }
            };
        }
        for (int i = 0; i < runners.length; i++)
            runners[i].run();
    }

    public static void main(String[] args) {
        new Access3().f();
    }
}
