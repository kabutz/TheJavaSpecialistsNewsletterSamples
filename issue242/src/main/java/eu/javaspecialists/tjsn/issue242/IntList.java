package eu.javaspecialists.tjsn.issue242;

import java.util.*;
import java.util.concurrent.locks.*;

public class IntList {
    private static final int OPTIMISTIC_SPIN = 3;
    private final StampedLock sl = new StampedLock();
    private int[] arr = new int[10];
    private int size = 0;

    /**
     * The size() method cannot be used to perform compound
     * operations, such as getting the last element of a list.
     * This code could fail with an IndexOutOfBoundsException:
     *
     * <pre>
     *   Thread1:
     *     while(true) {
     *       list.get(list.size()-1);
     *     }
     *
     *   Thread2:
     *     for (int i = 0; i < 2000; i++) {
     *       for (int j = 0; j < 100; j++) {
     *         list.add(j);
     *       }
     *       for (int j = 0; j < 50; j++) {
     *         list.remove(0);
     *       }
     *     }
     * </pre>
     */
    public int size() {
        // Internally, the tryOptimisticRead() is reading a volatile
        // field.  From a memory visibility perspective, reading a
        // volatile field is like entering a synchronized block.
        // We will thus not have an issue with stale values of size.
        // Note 1: volatile != synchronized.  Volatile does not
        // magically make compound operations on fields mutually
        // exclusive. Race conditions are more probable on volatile
        // fields.
        // Note 2: We could also have made size volatile.  From a
        // visibility perspective the tryOptimisticRead() will work,
        // but not if size was a long or if it could have
        // intermediate values that broke invariants.
        sl.tryOptimisticRead();
        return this.size;
    }

    public int get(int index) {
        for (int i = 0; i < OPTIMISTIC_SPIN; i++) {
            long stamp = sl.tryOptimisticRead();
            int size = this.size;
            int[] arr = this.arr;
            if (index < arr.length) {
                int r = arr[index];
                if (sl.validate(stamp)) {
                    rangeCheck(index, size);
                    return r;
                }
            }
        }
        long stamp = sl.readLock();
        try {
            rangeCheck(index, this.size);
            return this.arr[index];
        } finally {
            sl.unlockRead(stamp);
        }
    }

    public void trimToSize() {
        long stamp = sl.tryOptimisticRead();
        int currentSize = size;
        int[] currentArr = arr;
        if (sl.validate(stamp)) {
            // fast optimistic read to accelerate trimToSize() when
            // there is no work to do
            if (currentSize == currentArr.length) return;
        }
        stamp = sl.writeLock();
        try {
            if (size < arr.length) {
                arr = Arrays.copyOf(arr, size);
            }
        } finally {
            sl.unlockWrite(stamp);
        }
    }

    public boolean add(int e) {
        long stamp = sl.writeLock();
        try {
            if (size + 1 > arr.length)
                arr = Arrays.copyOf(arr, size + 10);

            arr[size++] = e;
            return true;
        } finally {
            sl.unlockWrite(stamp);
        }
    }

    // just to illustrate how an upgrade could be coded
    public int removeWithUpgrade(int index) {
        long stamp = sl.readLock();
        try {
            while (true) {
                rangeCheck(index, size);
                long writeStamp = sl.tryConvertToWriteLock(stamp);
                if (writeStamp == 0) {
                    sl.unlockRead(stamp);
                    stamp = sl.writeLock();
                } else {
                    stamp = writeStamp;
                    return doActualRemove(index);
                }
            }
        } finally {
            sl.unlock(stamp);
        }
    }

    public int remove(int index) {
        long stamp = sl.writeLock();
        try {
            rangeCheck(index, size);
            return doActualRemove(index);
        } finally {
            sl.unlock(stamp);
        }
    }

    private int doActualRemove(int index) {
        int oldValue = arr[index];

        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(arr, index + 1, arr, index, numMoved);
        arr[--size] = 0;

        return oldValue;
    }

    private static void rangeCheck(int index, int size) {
        if (index >= size)
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", Size: " + size);
    }
}
