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

package eu.javaspecialists.tjsn.issue217;

import org.junit.jupiter.api.*;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

public class NumberRangeCorrectnessTest {
    @Test
    public void testNumberRanges() {
        test(new NumberRangeSynchronized());
        test(new NumberRangeAtomic());
        test(new NumberRangeAtomicRef());
        test(new NumberRangeAtomicWithPark());
        test(new NumberRangeAtomicRefWithPark());
        test(new NumberRangeAtomicBroken());
    }

    private void test(NumberRange range) {
        assertTrue(range.isInRange(0));
        assertTrue(range.setUpper(100));
        assertFalse(range.setLower(200));
        assertTrue(range.setLower(50));
        assertTrue(range.isInRange(70));
        assertFalse(range.isInRange(0));
        assertTrue(range.setLower(Integer.MIN_VALUE));
        assertTrue(range.setUpper(Integer.MAX_VALUE));
        assertTrue(range.isInRange(70));
        assertTrue(range.isInRange(Integer.MAX_VALUE));
        assertTrue(range.isInRange(Integer.MAX_VALUE - 1));
        assertTrue(range.isInRange(Integer.MIN_VALUE));
        assertTrue(range.isInRange(Integer.MIN_VALUE + 1));
    }

    @Test
    public void testConcurrentCorrect() throws Exception {
        test(new NumberRangeSynchronized());
        test(new NumberRangeAtomic());
        test(new NumberRangeAtomicRef());
        test(new NumberRangeAtomicWithPark());
        test(new NumberRangeAtomicRefWithPark());
    }

    //  @Test // - fails test
    public void testConcurrentBroken() throws Exception {
        testConcurrently(new NumberRangeAtomicBroken());
        testConcurrently(new NumberRangeBroken());
    }

    private void testConcurrently(NumberRange range)
            throws InterruptedException {
        range.setLower(0);
        range.setUpper(200);
        ExecutorService pool = Executors.newCachedThreadPool();
        pool.submit(() -> {
            for (int i = 0; i < 100_000_000; i++) {
                range.setLower(100);
                range.setLower(0);
            }
        });
        pool.submit(() -> {
            for (int i = 0; i < 100_000_000; i++) {
                range.setUpper(40);
                range.setUpper(200);
            }
        });
        Future<Boolean> checker = pool.submit(() -> {
            for (int i = 0; i < 100_000_000; i++) {
                if (!range.checkCorrectRange()) return false;
            }
            return true;
        });
        try {
            assertTrue(checker.get());
        } catch (ExecutionException e) {
            fail(e.getCause().toString());
        }
        pool.shutdown();
    }
}
