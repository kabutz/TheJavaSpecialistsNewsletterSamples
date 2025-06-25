package eu.javaspecialists.tjsn.issue252;

import java.time.*;
import java.util.*;
import java.util.function.*;

/**
 * Takes a start date and iterates backwards one year at a time.
 */
public class YearSpliterator implements Spliterator<LocalDate> {
    private LocalDate date;

    public YearSpliterator(LocalDate startDate) {
        this.date = startDate;
    }

    public long estimateSize() {
        return Long.MAX_VALUE;
    }

    public boolean tryAdvance(Consumer<? super LocalDate> action) {
        Objects.requireNonNull(action);
        action.accept(date);
        date = date.minusYears(1);
        return true;
    }

    public Spliterator<LocalDate> trySplit() {
        return null;
    }

    public int characteristics() {
        return DISTINCT | IMMUTABLE | NONNULL | ORDERED | SORTED;
    }

    public Comparator<? super LocalDate> getComparator() {
        return Comparator.reverseOrder();
    }
}
