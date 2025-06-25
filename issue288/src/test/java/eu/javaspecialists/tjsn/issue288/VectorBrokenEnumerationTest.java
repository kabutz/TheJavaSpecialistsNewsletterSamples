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
import java.util.function.*;

import static java.util.Spliterator.*;

class VectorBrokenEnumerationTest {
    private static final Consumer<? super Integer> PRINTLN =
            i -> System.out.println("\t\t" + i);
    private Collection<Supplier<Vector<Integer>>> collectionSuppliers =
            List.of(Vector::new);

    ///////
    // Adding after current iteration position
    ///////

    /**
     * Purpose: Create an enumeration on an empty collection, then add
     * an element and see whether the enumeration finds it.
     * Correct: Prints 0.
     */
    @Test
    public void testEmptyIteratorThenAddingElement() {
        test(collection -> {
            Enumeration<Integer> en = collection.elements();
            collection.add(0);
            printRemaining(en);
        });
    }

    private void printRemaining(Enumeration<Integer> en) {
        while (en.hasMoreElements()) {
            PRINTLN.accept(en.nextElement());
        }
    }

    /**
     * Purpose: Create an iterator on a collection with a single
     * element, then add another element and see whether the
     * iterator finds both.
     * Correct: Prints 0, 1.
     */
    @Test
    public void testOneElementIteratorThenAddingElement() {
        test(collection -> {
            collection.add(0);
            Enumeration<Integer> en = collection.elements();
            collection.add(1);
            printRemaining(en);
        });
    }

    /**
     * Purpose: Create an iterator on a collection with two
     * elements, then add another element and see whether the
     * iterator finds all three.
     * Correct: Prints 0, 1, 2.
     */
    @Test
    public void testTwoElementsIteratorThenAddingElement() {
        test(collection -> {
            collection.add(0);
            collection.add(1);
            Enumeration<Integer> en = collection.elements();
            collection.add(2);
            printRemaining(en);
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
     * Incorrect: Prints 5, 5, 6
     */
    @Test
    public void testOneElementIteratorThenAddingElementBefore() {
        test(collection -> {
            collection.add(5);
            collection.add(6);
            Enumeration<Integer> en = collection.elements();
            PRINTLN.accept(en.nextElement());
            collection.add(0, 0); // happens to put it before the 5
            printRemaining(en);
        });
    }

    ///////
    // Clearing collection after iterator has been created
    ///////

    /**
     * Purpose: Create an iterator on a collection with a single
     * element, then clear it and see what iterator finds.
     * Correct: Prints nothing
     */
    @Test
    public void testOneIteratorThenClear() {
        test(collection -> {
            collection.add(0);
            Enumeration<Integer> en = collection.elements();
            collection.clear();
            printRemaining(en);
        });
    }

    ///////
    // Removing elements
    ///////


    /**
     * Purpose: Create an iterator on a collection with three
     * elements, then remove the first element.
     * Correct: 1, 2
     */
    @Test
    public void testThreeIteratorThenRemoveFirst() {
        test(collection -> {
            collection.add(0);
            collection.add(1);
            collection.add(2);
            Enumeration<Integer> en = collection.elements();
            collection.remove((Object) 0);
            printRemaining(en);
        });
    }

    /**
     * Purpose: Create an iterator on a collection with three
     * elements, then remove the second element.
     * Correct: Prints 0, 2
     */
    @Test
    public void testThreeIteratorThenRemoveSecond() {
        test(collection -> {
            collection.add(0);
            collection.add(1);
            collection.add(2);
            Enumeration<Integer> en = collection.elements();
            collection.remove((Object) 1);
            printRemaining(en);
        });
    }

    /**
     * Purpose: Create an iterator on a collection with three
     * elements, then advance the iterator and remove the first
     * element.
     * Incorrect: Prints 2
     */
    @Test
    public void testThreeIteratorNextThenRemoveFirst() {
        test(collection -> {
            collection.add(0);
            collection.add(1);
            collection.add(2);
            Enumeration<Integer> en = collection.elements();
            en.nextElement(); // 0
            collection.remove((Object) 0);
            printRemaining(en);
        });
    }

    /**
     * Purpose: Create an iterator on a collection with five
     * elements, then remove the first two and iterate.
     * Correct: Prints 2, 3, 4
     */
    @Test
    public void testRemovedFirstTwoItemsIteration() {
        test(collection -> {
            collection.add(0);
            collection.add(1);
            collection.add(2);
            collection.add(3);
            collection.add(4);
            Enumeration<Integer> en = collection.elements();
            collection.remove((Object) 0);
            collection.remove((Object) 1);
            printRemaining(en);
        });
    }

    private void test(Consumer<Vector<Integer>> test) {
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