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

package eu.javaspecialists.tjsn.issue288;

import org.junit.jupiter.api.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

import static java.util.Spliterator.*;

class WeaklyConsistentIterationTest {
    private static final int LENGTH = 5;
    private static final
    Collection<Supplier<Collection<Integer>>> collectionSuppliers =
            List.of(
                    () -> new ArrayBlockingQueue<>(LENGTH),
                    ConcurrentHashMap::newKeySet,
                    ConcurrentLinkedDeque::new,
                    ConcurrentLinkedQueue::new,
                    ConcurrentSkipListSet::new,
                    LinkedBlockingDeque::new,
                    LinkedBlockingQueue::new,
                    LinkedTransferQueue::new);

    private static final Consumer<? super Integer> PRINTLN =
            i -> System.out.println("\t\t" + i);

    @Test
    public void checkSpliterators() {
        test(collection -> {
            if (!concurrent(collection))
                throw new AssertionError("Expected to be concurrent");
        });
    }

    ///////
    // Adding after current iteration position
    ///////

    /**
     * Purpose: Create an iterator on an empty collection, then add
     * an element and see whether the iterator finds it.
     * Consistent: None of these iterators show the element.
     */
    @Test
    public void testEmptyIteratorThenAddingElement() {
        test(collection -> {
            Iterator<Integer> it = collection.iterator();
            collection.add(0);
            it.forEachRemaining(PRINTLN);
        });
    }

    /**
     * Purpose: Create an iterator on a collection with a single
     * element, then add another element and see whether the
     * iterator finds both.
     * Inconsistent: ArrayBlockingQueue is different from the
     * others. It only shows 0, the other classes show 0 and 1
     */
    @Test
    public void testOneElementIteratorThenAddingElement() {
        test(collection -> {
            collection.add(0);
            Iterator<Integer> it = collection.iterator();
            collection.add(1);
            it.forEachRemaining(PRINTLN);
        });
    }

    /**
     * Purpose: Create an iterator on a collection with two
     * elements, then add another element and see whether the
     * iterator finds all three.
     * Consistent: Now they all show all three elements. In Java 7
     * ArrayBlockingQueue showed 0 and 1.
     */
    @Test
    public void testTwoElementsIteratorThenAddingElement() {
        test(collection -> {
            collection.add(0);
            collection.add(1);
            Iterator<Integer> it = collection.iterator();
            collection.add(2);
            it.forEachRemaining(PRINTLN);
        });
    }

    ///////
    // Adding before current iteration position
    ///////

    /**
     * Purpose: Create an iterator on a collection with a single
     * element, then add another element before and see whether the
     * iterator finds both. This only works for some of the
     * collections.
     * Consistent: Those collections that work only show 5 and 6.
     */
    @Test
    public void testOneElementIteratorThenAddingElementBefore() {
        test(collection -> {
            collection.add(5);
            collection.add(6);
            Iterator<Integer> it = collection.iterator();
            PRINTLN.accept(it.next());
            if (!ordered(collection)) {
                System.out.println("\t\tunordered");
                collection.add(0); // happens to put it before the 5
            } else if (sorted(collection)) {
                System.out.println("\t\tsorted");
                collection.add(0);
            } else if (collection instanceof Deque<Integer> deque) {
                System.out.println("\t\tDeque");
                deque.addFirst(0);
            } else {
                System.out.println("\t\tskip ...");
                return;
            }
            it.forEachRemaining(PRINTLN);
        });
    }

    ///////
    // Clearing collection after iterator has been created
    ///////

    /**
     * Purpose: Create an iterator on a collection with a single
     * element, then clear it and see what iterator finds.
     * Consistent: They all show the element that was there before
     * the clear(). Actually does not matter how many elements
     * there were, we'd get back the first element, however that is
     * defined.
     */
    @Test
    public void testOneIteratorThenClear() {
        test(collection -> {
            collection.add(0);
            Iterator<Integer> it = collection.iterator();
            collection.clear();
            it.forEachRemaining(PRINTLN);
        });
    }

    ///////
    // Removing elements
    ///////


    /**
     * Purpose: Create an iterator on a collection with three
     * elements, then remove the first element.
     * Consistent: They all show 0, 1, 2
     */
    @Test
    public void testThreeIteratorThenRemoveFirst() {
        test(collection -> {
            collection.add(0);
            collection.add(1);
            collection.add(2);
            Iterator<Integer> it = collection.iterator();
            collection.remove(0);
            it.forEachRemaining(PRINTLN);
        });
    }

    /**
     * Purpose: Create an iterator on a collection with three
     * elements, then remove the second element.
     * Consistent: They all show 0, 2
     */
    @Test
    public void testThreeIteratorThenRemoveSecond() {
        test(collection -> {
            collection.add(0);
            collection.add(1);
            collection.add(2);
            Iterator<Integer> it = collection.iterator();
            collection.remove(1);
            it.forEachRemaining(PRINTLN);
        });
    }

    /**
     * Purpose: Create an iterator on a collection with three
     * elements, then advance the iterator and remove the first
     * element.
     * Consistent: They all show 1, 2
     */
    @Test
    public void testThreeIteratorNextThenRemoveFirst() {
        test(collection -> {
            collection.add(0);
            collection.add(1);
            collection.add(2);
            Iterator<Integer> it = collection.iterator();
            it.next(); // 0
            collection.remove(0);
            it.forEachRemaining(PRINTLN);
        });
    }

    /**
     * Purpose: Create an iterator on a collection with five
     * elements, then remove the first two and iterate.
     * Consistent: They all show 0, 2, 3, 4
     */
    @Test
    public void testRemovedFirstTwoItemsIteration() {
        test(collection -> {
            collection.add(0);
            collection.add(1);
            collection.add(2);
            collection.add(3);
            collection.add(4);
            Iterator<Integer> it = collection.iterator();
            collection.remove(0);
            collection.remove(1);
            it.forEachRemaining(PRINTLN);
        });
    }

    /**
     * Purpose: Create an iterator on a collection with five
     * elements, then remove them one by one, adding new elements.
     * Inconsistent: ArrayBlockingQueue shows 0, all others
     * show 0,10,11,12,13,14. In Java 7 ArrayBlockingQueue showed
     * 10,11,12,13,14.
     */
    @Test
    public void testDoubleWrapAroundAddingIteration() {
        test(collection -> {
            for (int i = 0; i < LENGTH; i++) collection.add(i);
            Iterator<Integer> it = collection.iterator();
            for (int i = 0; i < LENGTH * 2; i++) {
                collection.remove(i);
                collection.add(i + LENGTH);
            }
            it.forEachRemaining(PRINTLN);
        });
    }

    /**
     * Purpose: Removing last element with iterator after
     * forEachRemaining
     * Inconsistent: ArrayBlockingQueue throws IllegalStateException
     * Used to work as expected in Java 7.
     */
    @Test
    public void testThreeIteratorThenForEachRemainingRemove() {
        test(collection -> {
            collection.add(0);
            collection.add(1);
            collection.add(2);
            Iterator<Integer> it = collection.iterator();
            it.forEachRemaining(PRINTLN);
            it.remove();
        });
    }

    private void test(Consumer<Collection<Integer>> test) {
        System.out.println(
                new Throwable().getStackTrace()[1].getMethodName()
        );
        collectionSuppliers.stream()
                .map(Supplier::get)
                .forEach(collection -> {
                    System.out.println("\t" +
                            collection.getClass().getCanonicalName());
                    try {
                        test.accept(collection);
                    } catch (AssertionError | RuntimeException e) {
                        System.out.println("\t\t" + e);
                    }
                });
    }

    private boolean ordered(Collection<?> collection) {
        return characteristics(collection, ORDERED);
    }

    private boolean sorted(Collection<?> collection) {
        return characteristics(collection, SORTED);
    }

    private boolean concurrent(Collection<?> collection) {
        return characteristics(collection, CONCURRENT);
    }

    private boolean characteristics(Collection<?> c,
                                    int characteristics) {
        return c.spliterator().hasCharacteristics(characteristics);
    }

}