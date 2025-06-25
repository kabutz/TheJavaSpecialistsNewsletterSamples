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

package eu.javaspecialists.tjsn.issue149;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class ThreadCreationTest {
    public static void main(String... args)
            throws InterruptedException {
        final AtomicInteger threads_created = new AtomicInteger(0);
        while (true) {
            final CountDownLatch latch = new CountDownLatch(1);
            new Thread() {
                {start();}

                public void run() {
                    latch.countDown();
                    synchronized (this) {
                        System.out.println("threads created: " +
                                threads_created.incrementAndGet());
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            };
            latch.await();
        }
    }
}
