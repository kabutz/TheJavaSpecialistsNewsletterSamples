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
