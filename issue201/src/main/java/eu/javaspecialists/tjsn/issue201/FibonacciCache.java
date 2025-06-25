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

package eu.javaspecialists.tjsn.issue201;

import java.math.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

class FibonacciCache {
    private final ConcurrentMap<Integer, BigInteger> cache =
            new ConcurrentHashMap<>();

    private final Lock lock = new ReentrantLock();
    private final Condition solutionArrived = lock.newCondition();
    private final Set<Integer> cacheReservation = new HashSet<>();

    public BigInteger get(int n) throws InterruptedException {
        lock.lock();
        try {
            while (cacheReservation.contains(n)) {
                // we now want to wait until the answer is in the cache
                solutionArrived.await();
            }
            BigInteger result = cache.get(n);
            if (result != null) {
                return result;
            }

            BigInteger nMinusOne = cache.get(n - 1);
            BigInteger nMinusTwo = cache.get(n - 2);
            if (nMinusOne != null && nMinusTwo != null) {
                result = nMinusOne.add(nMinusTwo);
                put(n, result);
                return result;
            }
            BigInteger nPlusOne = cache.get(n + 1);
            BigInteger nPlusTwo = cache.get(n + 2);
            if (nPlusOne != null && nPlusTwo != null) {
                result = nPlusTwo.subtract(nPlusOne);
                put(n, result);
                return result;
            }
            if (nPlusOne != null && nMinusOne != null) {
                result = nPlusOne.subtract(nMinusOne);
                put(n, result);
                return result;
            }
            cacheReservation.add(n);
            return null;
        } finally {
            lock.unlock();
        }
    }

    public void put(int n, BigInteger value) {
        lock.lock();
        try {
            solutionArrived.signalAll();
            cacheReservation.remove(n);
            cache.putIfAbsent(n, value);
        } finally {
            lock.unlock();
        }
    }
}
