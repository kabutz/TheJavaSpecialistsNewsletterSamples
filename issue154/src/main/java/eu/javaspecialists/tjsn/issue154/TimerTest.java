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

package eu.javaspecialists.tjsn.issue154;

import java.util.*;

public class TimerTest {
    public static void main(String... args) {
        Timer timer = new Timer();
        for (int i = 0; i < 5; i++) {
            final int i1 = i;
            timer.schedule(new TimerTask() {
                public void run() {
                    System.out.println("i = " + i1);
                    if (Math.random() < 0.1) {
                        throw new RuntimeException();
                    }
                }
            }, 1000, 1000);
        }
    }
}
