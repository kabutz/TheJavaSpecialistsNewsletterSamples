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

package eu.javaspecialists.tjsn.issue262;

import java.util.concurrent.*;

public class BruteForceNestedLoopsParallel extends BruteForceBase {
    public static void main(String... args) {
        ThreadFactory factory = Executors.defaultThreadFactory();
        ExecutorService pool = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors(),
                r -> {
                    // always give threads descriptive names
                    Thread t = factory.newThread(r);
                    t.setName(t.getName() + "#bruteforce");
                    return t;
                }
        );
        int target = 0; // our target hash code
        for (byte i0 : alphabet) {
            pool.submit(() -> {
                int h0 = i0;
                for (byte i1 : alphabet) {
                    int h1 = h0 * 31 + i1;
                    for (byte i2 : alphabet) {
                        int h2 = h1 * 31 + i2;
                        for (byte i3 : alphabet) {
                            int h3 = h2 * 31 + i3;
                            for (byte i4 : alphabet) {
                                int h4 = h3 * 31 + i4;
                                for (byte i5 : alphabet) {
                                    int h5 = h4 * 31 + i5;
                                    for (byte i6 : alphabet) {
                                        int h6 = h5 * 31 + i6;
                                        if (h6 == target) {
                                            byte[] is = {i0, i1, i2, i3, i4, i5, i6};
                                            System.out.println(new String(is));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            });
        }
        pool.shutdown();
        // once all threads are done, we will naturally exit
    }
}
