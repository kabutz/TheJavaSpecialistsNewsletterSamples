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

package eu.javaspecialists.tjsn.issue171;

import junit.framework.*;

import java.util.*;
import java.util.concurrent.*;

public class FailFastCollectionTest extends TestCase {
    private final Collection<String> safe_names =
            new FailFastCollection<String>(new ArrayList<String>());

    protected void setUp() throws Exception {
        safe_names.clear();
        Collections.addAll(safe_names, "Anton", "John", "Heinz");
    }

    public void testBasicIteration() {
        for (String name : safe_names) {
        }
    }

    public void testBasicModification() {
        // we first iterate
        for (String name : safe_names) {
        }
        // then we add Dirk - should work as no client code has a
        // handle to the iterator and we ran the iterator to
        // completion
        safe_names.add("Dirk");
        assertEquals(4, safe_names.size());
        Iterator<String> it = safe_names.iterator();
        assertEquals("Anton", it.next());
        assertEquals("John", it.next());
        assertEquals("Heinz", it.next());
        assertEquals("Dirk", it.next());
    }

    public void testModificationFromAnotherThread() throws InterruptedException {
        // this test is more tricky.  Once we have iterated through
        // two elements, we want to add "Neil" to the list.  Since
        // that is done in another thread, we get the exception using
        // futures and check it is a ConcurrentModificationException
        final CountDownLatch latch = new CountDownLatch(2);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<?> future = executor.submit(new Callable<Object>() {
            public Object call() throws Exception {
                latch.await();
                safe_names.add("Neil");
                return null;
            }
        });

        for (String name : safe_names) {
            System.out.println(name);
            latch.countDown();
            Thread.sleep(50);
        }

        try {
            future.get();
            fail("expected a ConcurrentModificationException!");
        } catch (ExecutionException e) {
            if (e.getCause() instanceof ConcurrentModificationException) {
                // success
            } else {
                fail("expected a ConcurrentModificationException!");
            }
        }
    }


    public void testModificationFromSameThread() {
        // iteration does not run to completion, but iterator is out
        // of scope
        try {
            for (String name : safe_names) {
                if (name.equals("John")) {
                    safe_names.add("Andre");
                }
            }
        } catch (ConcurrentModificationException ex) {
            System.out.println("Expected the error " + ex);
        }

        // now we should be able to use the collection still.
        assertEquals(3, safe_names.size());
        Iterator<String> it = safe_names.iterator();
        assertEquals("Anton", it.next());
        assertEquals("John", it.next());
        assertEquals("Heinz", it.next());

        // we have run to completion, thus the iterator is inactive

        safe_names.add("Andre");

        assertEquals(4, safe_names.size());
        Iterator<String> it2 = safe_names.iterator();
        assertEquals("Anton", it2.next());
        assertEquals("John", it2.next());
        assertEquals("Heinz", it2.next());
        assertEquals("Andre", it2.next());
    }
}