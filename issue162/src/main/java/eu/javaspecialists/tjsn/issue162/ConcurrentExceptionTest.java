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

package eu.javaspecialists.tjsn.issue162;

import java.util.*;

public class ConcurrentExceptionTest {
    private static volatile boolean running = true;

    public static void main(String[] args)
            throws InterruptedException {
        final Collection shared = new ArrayList();
        Thread reader = new Thread("Reader") {
            public void run() {
                while (running) {
                    // ConcurrentModificationException happens here
                    for (Object o : shared) {
                    }
                }
            }
        };
        reader.start();

        Thread writer = new Thread("Writer") {
            public void run() {
                while (running) {
                    // the thread modifying the collection does
                    // not see any exception
                    shared.add("Hello");
                    shared.remove("Hello");
                }
            }
        };
        writer.start();

        Thread.sleep(2000);

        System.out.println("reader alive? " + reader.isAlive());
        System.out.println("writer alive? " + writer.isAlive());
        running = false;
    }
}
