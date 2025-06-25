package eu.javaspecialists.tjsn.issue217;

import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class NumberRangeAtomicRefWithPark implements NumberRange {
    private static final class Range {
        private final int lower, upper;

        private Range(int lower, int upper) {
            this.lower = lower;
            this.upper = upper;
        }

        public boolean isInRange(int number) {
            return number >= lower && number <= upper;
        }
    }

    private final AtomicReference<Range> range =
            new AtomicReference<>(new Range(0, 0));

    public boolean setLower(int newLower) {
        while (true) {
            Range current = range.get();
            if (newLower > current.upper) return false;
            Range next = new Range(newLower, current.upper);
            if (range.compareAndSet(current, next)) return true;
            // now we sleep a constant time
            LockSupport.parkNanos(180_000);
        }
    }

    public boolean setUpper(int newUpper) {
        while (true) {
            Range current = range.get();
            if (newUpper < current.lower) return false;
            Range next = new Range(current.lower, newUpper);
            if (range.compareAndSet(current, next)) return true;
            // now we sleep a constant time
            LockSupport.parkNanos(180_000);
        }
    }

    public boolean isInRange(int number) {
        return range.get().isInRange(number);
    }

    public boolean checkCorrectRange() {
        Range range = this.range.get();
        return range.lower <= range.upper;
    }
}
