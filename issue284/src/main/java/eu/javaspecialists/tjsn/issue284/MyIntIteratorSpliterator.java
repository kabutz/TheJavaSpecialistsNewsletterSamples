package eu.javaspecialists.tjsn.issue284;

import java.util.*;
import java.util.function.*;

public class MyIntIteratorSpliterator implements Spliterator.OfInt {
    static final int BATCH_UNIT = 1 << 10;  // batch array size increment
    static final int MAX_BATCH = 1 << 25;  // max batch array size;
    private final MyIntIterator it;
    private final int characteristics;
    private long est;             // size estimate
    private int batch;            // batch size for splits

    public MyIntIteratorSpliterator(MyIntIterator iterator, int characteristics) {
        this.it = iterator;
        this.est = Long.MAX_VALUE;
        this.characteristics = characteristics &
                ~(Spliterator.SIZED | Spliterator.SUBSIZED);
    }

    public OfInt trySplit() {
        long s = est;
        if (s > 1 && it.hasNext()) {
            int n = batch + BATCH_UNIT;
            if (n > s)
                n = (int) s;
            if (n > MAX_BATCH)
                n = MAX_BATCH;
            int[] a = new int[n];
            int j = 0;
            do { a[j] = it.next(); }
            while (++j < n && it.hasNext());
            batch = j;
            if (est != Long.MAX_VALUE)
                est -= j;
            return new MyIntArraySpliterator(a, 0, j, characteristics);
        }
        return null;
    }

    public void forEachRemaining(IntConsumer action) {
        if (action == null) throw new NullPointerException();
        it.forEachRemaining(action);
    }

    public boolean tryAdvance(IntConsumer action) {
        if (action == null) throw new NullPointerException();
        if (it.hasNext()) {
            action.accept(it.next());
            return true;
        }
        return false;
    }

    public long estimateSize() {
        return est;
    }

    public int characteristics() {
        return characteristics;
    }

    private static final class MyIntArraySpliterator implements OfInt {
        private final int[] array;
        private int index;        // current index, modified on advance/split
        private final int fence;  // one past last index
        private final int characteristics;

        public MyIntArraySpliterator(int[] array, int origin, int fence,
                                     int additionalCharacteristics) {
            this.array = array;
            this.index = origin;
            this.fence = fence;
            this.characteristics = additionalCharacteristics |
                    Spliterator.SIZED | Spliterator.SUBSIZED;
        }

        public OfInt trySplit() {
            int lo = index, mid = (lo + fence) >>> 1;
            return (lo >= mid)
                    ? null
                    : new MyIntArraySpliterator(array, lo, index = mid, characteristics);
        }

        public void forEachRemaining(IntConsumer action) {
            if (action == null)
                throw new NullPointerException();
            int[] a;
            int i, hi; // hoist accesses and checks from loop
            if ((a = array).length >= (hi = fence) &&
                    (i = index) >= 0 && i < (index = hi)) {
                do { action.accept(a[i]); } while (++i < hi);
            }
        }

        public boolean tryAdvance(IntConsumer action) {
            if (action == null)
                throw new NullPointerException();
            if (index >= 0 && index < fence) {
                action.accept(array[index++]);
                return true;
            }
            return false;
        }

        public long estimateSize() {
            return (long) (fence - index);
        }

        public int characteristics() {
            return characteristics;
        }

        public Comparator<? super Integer> getComparator() {
            if (hasCharacteristics(Spliterator.SORTED))
                return null;
            throw new IllegalStateException();
        }
    }
}