package eu.javaspecialists.tjsn.issue217;

import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class NumberRangeAtomicWithPark implements NumberRange {
    private final AtomicLong range = new AtomicLong(0);

    public boolean setLower(int newLower) {
        while (true) {
            long current = range.get();
            int upper = getUpper(current);
            int lower = getLower(current);
            if (newLower > upper) return false;
            long next = combine(newLower, upper);
            if (range.compareAndSet(current, next)) return true;
            // now we sleep a constant time
            LockSupport.parkNanos(180_000);
        }
    }

    public boolean setUpper(int newUpper) {
        while (true) {
            long current = range.get();
            int upper = getUpper(current);
            int lower = getLower(current);
            if (newUpper < lower) return false;
            long next = combine(lower, newUpper);
            if (range.compareAndSet(current, next)) return true;
            // now we sleep a constant time
            LockSupport.parkNanos(180_000);
        }
    }

    public boolean isInRange(int number) {
        long current = range.get();
        int upper = getUpper(current);
        int lower = getLower(current);
        return number >= lower && number <= upper;
    }

    private long combine(long lower, long upper) {
        return upper | (lower << 32);
    }

    private int getLower(long range) {
        return (int) (range >>> 32);
    }

    private int getUpper(long range) {
        return (int) (range);
    }

    public boolean checkCorrectRange() {
        long range = this.range.get();
        int lower = getLower(range);
        int upper = getUpper(range);
        return lower <= upper;
    }
}