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

package eu.javaspecialists.tjsn.issue261;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

public class QueueSize {
    public static final int RESIDENT_QUEUE_SIZE = 10;
    public static final int ADD_AND_REMOVES = 1_000_000;

    public static void main(String... args) {
        for (int i = 0; i < 10; i++) {
            // size either 10 or 11
            test(LinkedBlockingQueue::new);

            // size always >= 10
            test(LinkedTransferQueue::new);
            test(ConcurrentLinkedQueue::new);
            System.out.println();
        }
    }

    private static void test(Supplier<Queue<String>> queueType) {
        var queue = queueType.get();
        for (int i = 0; i < RESIDENT_QUEUE_SIZE; i++) {
            queue.add("test" + i);
        }

        var thread = addRemoveThread(queue);

        var maxSize = 0;
        while (thread.isAlive()) {
            maxSize = Math.max(maxSize, queue.size());
        }
        System.out.printf(Locale.US, "%s: maxSize=%d%n",
                queue.getClass().getSimpleName(), maxSize);
    }

    private static Thread addRemoveThread(Queue<String> queue) {
        var thread = new Thread(() -> {
            for (int i = 0; i < ADD_AND_REMOVES; i++) {
                queue.add("test" + (i + RESIDENT_QUEUE_SIZE));
                queue.remove();
            }
        }, "addRemoveThread");
        thread.start();
        return thread;
    }
}
