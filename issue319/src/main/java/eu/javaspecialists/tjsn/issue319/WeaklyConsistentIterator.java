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

package eu.javaspecialists.tjsn.issue319;

import java.util.*;
import java.util.concurrent.*;

public class WeaklyConsistentIterator {
    // Java 23-preview simpler version of "main"
    public void main() {
        test(new LinkedList<>());
        test(new LinkedBlockingQueue<>());
        test(new ArrayBlockingQueue<>(5));
    }

    private void test(Queue<Integer> queue) {
        System.out.println("Testing: " + queue.getClass());
        try {
            // fill up the queue with 5 numbers 0..4
            System.out.println("Adding 0..4");
            for (int i = 0; i < 5; i++) queue.add(i);
            System.out.println("queue = " + queue);

            // create an iterator
            var iterator = queue.iterator();

            // show the first three items in the iterator
            System.out.println("Iterating over the first 3 items");
            for (int i = 0; i < 3; i++) {
                System.out.println(iterator.next());
            }

            // replace the items in the queue with 5..9
            System.out.println("Replacing items in queue with 5..9");
            for (int i = 5; i < 10; i++) {
                queue.poll();
                queue.add(i);
            }
            System.out.println("queue = " + queue);

            // show the next five items in the iterator
            System.out.println("Iterating over the next 5 items");
            for (int i = 0; i < 5; i++)
                System.out.println(iterator.next());

            // Keep on removing and adding items until we have 10..19
            System.out.println("Replace items until we have 10..19");
            for (int i = 10; i < 20; i++) {
                queue.poll();
                queue.add(i);
            }
            System.out.println("queue = " + queue);

            // Show the remaining items in the iterator
            while (iterator.hasNext())
                System.out.println(iterator.next());
        } catch (ConcurrentModificationException ex) {
            System.out.println(ex);
        }
        System.out.println();
    }
}
