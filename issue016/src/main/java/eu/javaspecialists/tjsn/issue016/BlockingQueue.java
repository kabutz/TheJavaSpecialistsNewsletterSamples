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

package eu.javaspecialists.tjsn.issue016;

import java.util.*;

public class BlockingQueue {
    /**
     * It makes logical sense to use a linked list for a FIFO queue,
     * although an ArrayList is usually more efficient for a short
     * queue (on most VMs).
     */
    private final LinkedList queue = new LinkedList();

    /**
     * This method pushes an object onto the end of the queue, and
     * then notifies one of the waiting threads.
     */
    public void push(Object o) {
        synchronized (queue) {
            queue.add(o);
            queue.notify();
        }
    }

    /**
     * The pop operation blocks until either an object is returned
     * or the thread is interrupted, in which case it throws an
     * InterruptedException.
     */
    public Object pop() throws InterruptedException {
        synchronized (queue) {
            while (queue.isEmpty()) {
                queue.wait();
            }
            return queue.removeFirst();
        }
    }

    /**
     * Return the number of elements currently in the queue.
     */
    public int size() {
        synchronized (queue) {
            return queue.size();
        }
    }
}
