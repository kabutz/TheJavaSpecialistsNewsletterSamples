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

package eu.javaspecialists.tjsn.issue322;

import java.util.concurrent.*;

public class PutOnBlockingQueue {
    public static void main(String... args) {
        test(new ArrayBlockingQueue<>(10));
        test(new LinkedBlockingQueue<>());
        test(new LinkedTransferQueue<>());
        test(new PriorityBlockingQueue<>());
        test(new SynchronousQueue<>());
    }

    public static void test(BlockingQueue<Integer> queue) {
        System.out.print(queue.getClass().getSimpleName());
        Thread.interrupted(); // clear interrupt
        queue.offer(42); // might not add if no available capacity
        try {
            Thread.currentThread().interrupt(); // self-interrupt
            queue.put(53); // shouldn't really go into the WAITING state
            System.out.println(" - no early interrupt on put()");
        } catch (InterruptedException e) {
            System.out.println(" - early interrupt on put()");
        }
    }
}
