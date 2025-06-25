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

package eu.javaspecialists.tjsn.issue265;

import java.util.concurrent.atomic.*;

import static org.junit.jupiter.api.Assertions.*;

class DebouncerTest {
    // pass in Debouncer with interval of 1 second
    public void test(Debouncer<Runnable> db) throws InterruptedException {
        AtomicInteger rx_count = new AtomicInteger();
        AtomicInteger ry_count = new AtomicInteger();

        Runnable rx = () -> {
            System.out.println("x");
            rx_count.incrementAndGet();
        };
        Runnable ry = () -> {
            System.out.println("y");
            ry_count.incrementAndGet();
        };

        for (int i = 0; i < 8; i++) {
            Thread.sleep(50);
            db.call(rx);
            Thread.sleep(50);
            db.call(ry);
        }
        Thread.sleep(200); // expecting x and y
        assertEquals(1, rx_count.get());
        assertEquals(1, ry_count.get());

        for (int i = 0; i < 10000; i++) {
            db.call(rx);
        }
        Thread.sleep(2_400); // expecting only x
        assertEquals(2, rx_count.get());
        assertEquals(1, ry_count.get());

        db.call(ry);
        Thread.sleep(1_100); // expecting only y
        assertEquals(2, rx_count.get());
        assertEquals(2, ry_count.get());
        db.shutdown();
    }

}