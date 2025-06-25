package eu.javaspecialists.tjsn.issue295;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

class Streams {
    private static final int DISTINCT = Spliterator.DISTINCT; // 0x1
    private static final int OPERATED_ON = 0x2;
    private static final int SORTED = Spliterator.SORTED; // 0x4
    private static final int CLOSED = 0x8;
    private static final int ORDERED = Spliterator.ORDERED; // 0x10
    private static final int PARALLEL = 0x20;
    private static final int SIZED = Spliterator.SIZED; // 0x40
    private static final int NONNULL = Spliterator.NONNULL; // 0x100
    private static final int IMMUTABLE = Spliterator.IMMUTABLE; // 0x400
    private static final int CONCURRENT = Spliterator.CONCURRENT; // 0x1000
    private static final int SUBSIZED = Spliterator.SUBSIZED; // 0x4000
    private static final int EXTRA_FLAGS = OPERATED_ON | CLOSED | PARALLEL;

    private static sealed class EmptyBaseStream {
        private int state = 0;

        public EmptyBaseStream(EmptyBaseStream input) {
            this.state = input.state & ~(OPERATED_ON);
        }

        public EmptyBaseStream(Spliterator<?> spliterator) {
            this.state = spliterator.characteristics();
        }

        protected final void checkIfOperatedOnOrClosed() {
            if ((state & (OPERATED_ON | CLOSED)) != 0) {
                throw new IllegalStateException(
                        "stream has already been operated upon or closed"
                );
            }
        }

        protected final void checkIfOperatedOnOrClosedAndChangeState() {
            checkIfOperatedOnOrClosed();
            state |= OPERATED_ON;
        }

        protected final void stateDistinct() {
            state |= DISTINCT;
            state &= ~(SIZED | SUBSIZED | CONCURRENT | NONNULL | IMMUTABLE);
            if (hasComparator()) state &= ~SORTED;
        }

        protected final void stateDistinctPrimitiveStream() {
            state &= ~(SIZED | SUBSIZED | IMMUTABLE | SORTED);
        }

        protected void stateSorted() {
            state |= SORTED | ORDERED;
            state &= ~(CONCURRENT | NONNULL | IMMUTABLE);
        }

        protected final boolean isSorted() {
            return (state & SORTED) == SORTED;
        }

        protected boolean unorderedSame() {
            if ((state & ORDERED) == ORDERED) {
                state &= ~(CONCURRENT | NONNULL | ORDERED | IMMUTABLE);
                if (hasComparator()) state &= ~SORTED;
                state |= OPERATED_ON;
                return false;
            }
            return true;
        }

        protected final int stateBareCharacteristics() {
            return state & ~EXTRA_FLAGS;
        }

        public final void close() {
            // nothing to do
            state |= CLOSED;
        }

        public boolean isParallel() {
            return false;
        }

        protected final void checkParametersAndThenState(Object parameter) {
            Objects.requireNonNull(parameter);
            checkIfOperatedOnOrClosedAndChangeState();
        }

        protected final void checkParametersAndThenState(Object parameter1, Object parameter2) {
            Objects.requireNonNull(parameter1);
            Objects.requireNonNull(parameter2);
            checkIfOperatedOnOrClosedAndChangeState();
        }

        protected final void checkParametersAndThenState(Object parameter1, Object parameter2, Object parameter3) {
            Objects.requireNonNull(parameter1);
            Objects.requireNonNull(parameter2);
            Objects.requireNonNull(parameter3);
            checkIfOperatedOnOrClosedAndChangeState();
        }

        protected final void checkStateAndThenParameters(Object parameter) {
            // for some of the methods, we first check the state and then the parameter
            checkIfOperatedOnOrClosedAndChangeState();
            Objects.requireNonNull(parameter);
        }

        protected final <R> EmptyStream<R> nextEmptyStream(Object parameter) {
            checkParametersAndThenState(parameter);
            return new EmptyStream<>(this);
        }

        protected final EmptyIntStream nextEmptyIntStream(Object parameter) {
            checkParametersAndThenState(parameter);
            return new EmptyIntStream(this);
        }

        protected final EmptyLongStream nextEmptyLongStream(Object parameter) {
            checkParametersAndThenState(parameter);
            return new EmptyLongStream(this);
        }

        protected final EmptyDoubleStream nextEmptyDoubleStream(Object parameter) {
            checkParametersAndThenState(parameter);
            return new EmptyDoubleStream(this);
        }

        protected boolean hasComparator() {
            return false;
        }
    }


    /**
     * EmptyStream is an optimization to reduce object allocation
     * during stream creation for empty streams. Most of the
     * methods such as filter() and map() will return "this".
     * We have tried to mirror the behavior of the previous
     * Stream.empty() for spliterator characteristics, parallel()
     * and
     *
     * @param <T>
     */
    static final class EmptyStream<T> extends EmptyBaseStream implements Stream<T> {
        private final Comparator<? super T> comparator;

        public EmptyStream(EmptyBaseStream input) {
            super(input);
            comparator = null;
        }

        public EmptyStream(Spliterator<T> spliterator) {
            super(spliterator);
            comparator = spliterator.hasCharacteristics(Spliterator.SORTED) ?
                    spliterator.getComparator() : null;
        }

        @Override
        public Stream<T> filter(Predicate<? super T> predicate) {
            return nextEmptyStream(predicate);
        }

        @Override
        public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
            return nextEmptyStream(mapper);
        }

        @Override
        public IntStream mapToInt(ToIntFunction<? super T> mapper) {
            return nextEmptyIntStream(mapper);
        }

        @Override
        public LongStream mapToLong(ToLongFunction<? super T> mapper) {
            return nextEmptyLongStream(mapper);
        }

        @Override
        public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
            return nextEmptyDoubleStream(mapper);
        }

        @Override
        public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
            return nextEmptyStream(mapper);
        }

        @Override
        public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
            return nextEmptyIntStream(mapper);
        }

        @Override
        public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
            return nextEmptyLongStream(mapper);
        }

        @Override
        public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
            return nextEmptyDoubleStream(mapper);
        }

        @Override
        public <R> Stream<R> mapMulti(BiConsumer<? super T, ? super Consumer<R>> mapper) {
            return nextEmptyStream(mapper);
        }

        @Override
        public IntStream mapMultiToInt(BiConsumer<? super T, ? super IntConsumer> mapper) {
            return nextEmptyIntStream(mapper);
        }

        @Override
        public LongStream mapMultiToLong(BiConsumer<? super T, ? super LongConsumer> mapper) {
            return nextEmptyLongStream(mapper);
        }

        @Override
        public DoubleStream mapMultiToDouble(BiConsumer<? super T, ? super DoubleConsumer> mapper) {
            return nextEmptyDoubleStream(mapper);
        }

        @Override
        public Stream<T> distinct() {
            checkIfOperatedOnOrClosedAndChangeState();
            super.stateDistinct();
            return new EmptyStream<>(this);
        }

        @Override
        protected boolean hasComparator() {
            return comparator != null;
        }

        @Override
        public Stream<T> sorted() {
            checkIfOperatedOnOrClosedAndChangeState();
            super.stateSorted();
            return new EmptyStream<>(this);
        }

        @Override
        public Stream<T> sorted(Comparator<? super T> comparator) {
            checkStateAndThenParameters(comparator);
            return new EmptyStream<>(this);
        }

        @Override
        public Stream<T> peek(Consumer<? super T> action) {
            return nextEmptyStream(action);
        }

        @Override
        public Stream<T> limit(long maxSize) {
            if (maxSize < 0)
                throw new IllegalArgumentException(Long.toString(maxSize));
            checkIfOperatedOnOrClosedAndChangeState();
            return new EmptyStream<>(this);
        }

        @Override
        public Stream<T> skip(long n) {
            if (n < 0)
                throw new IllegalArgumentException(Long.toString(n));
            if (n == 0) return this;
            checkIfOperatedOnOrClosedAndChangeState();
            return new EmptyStream<>(this);
        }

        @Override
        public Stream<T> takeWhile(Predicate<? super T> predicate) {
            return nextEmptyStream(predicate);
        }

        @Override
        public Stream<T> dropWhile(Predicate<? super T> predicate) {
            return nextEmptyStream(predicate);
        }

        @Override
        public void forEach(Consumer<? super T> action) {
            checkStateAndThenParameters(action);
            // do nothing
        }

        @Override
        public void forEachOrdered(Consumer<? super T> action) {
            checkStateAndThenParameters(action);
            // do nothing
        }

        private static final Object[] EMPTY_ARRAY = {};

        @Override
        public Object[] toArray() {
            checkIfOperatedOnOrClosedAndChangeState();
            return EMPTY_ARRAY;
        }

        @Override
        public <A> A[] toArray(IntFunction<A[]> generator) {
            checkStateAndThenParameters(generator);
            return Objects.requireNonNull(generator.apply(0));
        }

        @Override
        public T reduce(T identity, BinaryOperator<T> accumulator) {
            checkParametersAndThenState(accumulator);
            return identity;
        }

        @Override
        public Optional<T> reduce(BinaryOperator<T> accumulator) {
            checkParametersAndThenState(accumulator);
            return Optional.empty();
        }

        @Override
        public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
            checkParametersAndThenState(accumulator, combiner);
            return identity;
        }

        @Override
        public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
            checkParametersAndThenState(supplier, accumulator, combiner);
            return supplier.get();
        }

        @Override
        public <R, A> R collect(Collector<? super T, A, R> collector) {
            checkParametersAndThenState(collector);
            return collector.finisher().apply(collector.supplier().get());
        }

        @Override
        public List<T> toList() {
            checkIfOperatedOnOrClosedAndChangeState();
            return List.of();
        }

        @Override
        public Optional<T> min(Comparator<? super T> comparator) {
            checkParametersAndThenState(comparator);
            return Optional.empty();
        }

        @Override
        public Optional<T> max(Comparator<? super T> comparator) {
            checkParametersAndThenState(comparator);
            return Optional.empty();
        }

        @Override
        public long count() {
            checkIfOperatedOnOrClosedAndChangeState();
            return 0L;
        }

        @Override
        public boolean anyMatch(Predicate<? super T> predicate) {
            checkParametersAndThenState(predicate);
            return false;
        }

        @Override
        public boolean allMatch(Predicate<? super T> predicate) {
            checkParametersAndThenState(predicate);
            return true;
        }

        @Override
        public boolean noneMatch(Predicate<? super T> predicate) {
            checkParametersAndThenState(predicate);
            return true;
        }

        @Override
        public Optional<T> findFirst() {
            checkIfOperatedOnOrClosedAndChangeState();
            return Optional.empty();
        }

        @Override
        public Optional<T> findAny() {
            checkIfOperatedOnOrClosedAndChangeState();
            return Optional.empty();
        }

        @Override
        public Iterator<T> iterator() {
            checkIfOperatedOnOrClosedAndChangeState();
            return Collections.emptyIterator();
        }

        @Override
        public Spliterator<T> spliterator() {
            checkIfOperatedOnOrClosedAndChangeState();
            if (isSorted())
                return new EmptySpliterator.OfRefSorted<>(stateBareCharacteristics(), comparator);
            else
                return new EmptySpliterator.OfRef<>(stateBareCharacteristics());
        }

        @Override
        public Stream<T> sequential() {
            return this;
        }

        @Override
        public Stream<T> parallel() {
            return StreamSupport.stream(spliterator(), true);
        }

        @Override
        public Stream<T> unordered() {
            checkIfOperatedOnOrClosed();
            if (super.unorderedSame()) return this;
            else return new EmptyStream<>(this);
        }

        @Override
        public Stream<T> onClose(Runnable closeHandler) {
            return StreamSupport.stream(
                    spliterator(), isParallel()
            ).onClose(closeHandler);
        }
    }

    // TODO: https://github.com/marschall > 4. I made my `EmptyBaseStream`
    //  implement `BaseStream` and make `EmptyIntLongDoubleStream` extend
    //  from this class as `IntLongDoubleStream` all extend `BaseStream`.
    //  This allowed me to move the following methods up in the hierarchy
    //  `#isParallel` , `#onClose`, `#sequential`, `#parallel`, `#unordered`.
    static final class EmptyIntStream extends EmptyBaseStream implements IntStream {
        public EmptyIntStream(EmptyBaseStream input) {
            super(input);
        }

        public EmptyIntStream(Spliterator.OfInt spliterator) {
            super(spliterator);
        }

        @Override
        public IntStream filter(IntPredicate predicate) {
            return nextEmptyIntStream(predicate);
        }

        @Override
        public IntStream map(IntUnaryOperator mapper) {
            return nextEmptyIntStream(mapper);
        }

        @Override
        public <U> Stream<U> mapToObj(IntFunction<? extends U> mapper) {
            return nextEmptyStream(mapper);
        }

        @Override
        public LongStream mapToLong(IntToLongFunction mapper) {
            return nextEmptyLongStream(mapper);
        }

        @Override
        public DoubleStream mapToDouble(IntToDoubleFunction mapper) {
            return nextEmptyDoubleStream(mapper);
        }

        @Override
        public IntStream flatMap(IntFunction<? extends IntStream> mapper) {
            return nextEmptyIntStream(mapper);
        }

        @Override
        public IntStream mapMulti(IntMapMultiConsumer mapper) {
            return nextEmptyIntStream(mapper);
        }

        @Override
        public IntStream distinct() {
            checkIfOperatedOnOrClosedAndChangeState();
            super.stateDistinctPrimitiveStream();
            return new EmptyIntStream(this);
        }

        @Override
        public IntStream sorted() {
            checkIfOperatedOnOrClosedAndChangeState();
            super.stateSorted();
            return new EmptyIntStream(this);
        }

        @Override
        public IntStream peek(IntConsumer action) {
            return nextEmptyIntStream(action);
        }

        @Override
        public IntStream limit(long maxSize) {
            if (maxSize < 0)
                throw new IllegalArgumentException(Long.toString(maxSize));
            checkIfOperatedOnOrClosedAndChangeState();
            return new EmptyIntStream(this);
        }

        @Override
        public IntStream skip(long n) {
            if (n < 0)
                throw new IllegalArgumentException(Long.toString(n));
            if (n == 0) return this;
            checkIfOperatedOnOrClosedAndChangeState();
            return new EmptyIntStream(this);
        }

        @Override
        public IntStream takeWhile(IntPredicate predicate) {
            return nextEmptyIntStream(predicate);
        }

        @Override
        public IntStream dropWhile(IntPredicate predicate) {
            return nextEmptyIntStream(predicate);
        }

        @Override
        public void forEach(IntConsumer action) {
            checkStateAndThenParameters(action);
            // do nothing
        }

        @Override
        public void forEachOrdered(IntConsumer action) {
            checkStateAndThenParameters(action);
            // do nothing
        }

        private static final int[] EMPTY_ARRAY = {};

        @Override
        public int[] toArray() {
            checkIfOperatedOnOrClosedAndChangeState();
            return EMPTY_ARRAY;
        }

        @Override
        public int reduce(int identity, IntBinaryOperator op) {
            checkParametersAndThenState(op);
            return identity;
        }

        @Override
        public OptionalInt reduce(IntBinaryOperator op) {
            checkParametersAndThenState(op);
            return OptionalInt.empty();
        }

        @Override
        public <R> R collect(Supplier<R> supplier, ObjIntConsumer<R> accumulator, BiConsumer<R, R> combiner) {
            checkParametersAndThenState(supplier, accumulator, combiner);
            return supplier.get();
        }

        @Override
        public OptionalInt min() {
            checkIfOperatedOnOrClosedAndChangeState();
            return OptionalInt.empty();
        }

        @Override
        public OptionalInt max() {
            checkIfOperatedOnOrClosedAndChangeState();
            return OptionalInt.empty();
        }

        @Override
        public long count() {
            checkIfOperatedOnOrClosedAndChangeState();
            return 0L;
        }

        @Override
        public boolean anyMatch(IntPredicate predicate) {
            checkParametersAndThenState(predicate);
            return false;
        }

        @Override
        public boolean allMatch(IntPredicate predicate) {
            checkParametersAndThenState(predicate);
            return true;
        }

        @Override
        public boolean noneMatch(IntPredicate predicate) {
            checkParametersAndThenState(predicate);
            return true;
        }

        @Override
        public OptionalInt findFirst() {
            checkIfOperatedOnOrClosedAndChangeState();
            return OptionalInt.empty();
        }

        @Override
        public OptionalInt findAny() {
            checkIfOperatedOnOrClosedAndChangeState();
            return OptionalInt.empty();
        }

        private static final PrimitiveIterator.OfInt EMPTY_ITERATOR =
                new PrimitiveIterator.OfInt() {
                    @Override
                    public int nextInt() {
                        throw new NoSuchElementException();
                    }

                    @Override
                    public boolean hasNext() {
                        return false;
                    }
                };


        @Override
        public PrimitiveIterator.OfInt iterator() {
            checkIfOperatedOnOrClosedAndChangeState();
            return EMPTY_ITERATOR;
        }

        @Override
        public Spliterator.OfInt spliterator() {
            checkIfOperatedOnOrClosedAndChangeState();
            return new EmptySpliterator.OfInt(stateBareCharacteristics());
        }

        @Override
        public IntStream sequential() {
            return this;
        }

        @Override
        public IntStream parallel() {
            return StreamSupport.intStream(spliterator(), true);
        }

        @Override
        public IntStream unordered() {
            checkIfOperatedOnOrClosed();
            if (super.unorderedSame()) return this;
            else return new EmptyIntStream(this);
        }

        @Override
        public IntStream onClose(Runnable closeHandler) {
            return StreamSupport.intStream(
                    spliterator(), isParallel()
            ).onClose(closeHandler);
        }

        @Override
        public int sum() {
            checkIfOperatedOnOrClosedAndChangeState();
            return 0;
        }

        @Override
        public OptionalDouble average() {
            checkIfOperatedOnOrClosedAndChangeState();
            return OptionalDouble.empty();
        }

        @Override
        public IntSummaryStatistics summaryStatistics() {
            checkIfOperatedOnOrClosedAndChangeState();
            return new IntSummaryStatistics();
        }

        @Override
        public LongStream asLongStream() {
            checkIfOperatedOnOrClosedAndChangeState();
            return new EmptyLongStream(this);
        }

        @Override
        public DoubleStream asDoubleStream() {
            checkIfOperatedOnOrClosedAndChangeState();
            return new EmptyDoubleStream(this);
        }

        @Override
        public Stream<Integer> boxed() {
            checkIfOperatedOnOrClosedAndChangeState();
            return new EmptyStream<>(this);
        }
    }

    static final class EmptyLongStream extends EmptyBaseStream implements LongStream {
        public EmptyLongStream(EmptyBaseStream input) {
            super(input);
        }

        public EmptyLongStream(Spliterator.OfLong spliterator) {
            super(spliterator);
        }

        @Override
        public LongStream filter(LongPredicate predicate) {
            return nextEmptyLongStream(predicate);
        }

        @Override
        public LongStream map(LongUnaryOperator mapper) {
            return nextEmptyLongStream(mapper);
        }

        @Override
        public <U> Stream<U> mapToObj(LongFunction<? extends U> mapper) {
            return nextEmptyStream(mapper);
        }

        @Override
        public IntStream mapToInt(LongToIntFunction mapper) {
            return nextEmptyIntStream(mapper);
        }

        @Override
        public DoubleStream mapToDouble(LongToDoubleFunction mapper) {
            return nextEmptyDoubleStream(mapper);
        }

        @Override
        public LongStream flatMap(LongFunction<? extends LongStream> mapper) {
            return nextEmptyLongStream(mapper);
        }

        @Override
        public LongStream mapMulti(LongMapMultiConsumer mapper) {
            return nextEmptyLongStream(mapper);
        }

        @Override
        public LongStream distinct() {
            checkIfOperatedOnOrClosedAndChangeState();
            super.stateDistinctPrimitiveStream();
            return new EmptyLongStream(this);
        }

        @Override
        public LongStream sorted() {
            checkIfOperatedOnOrClosedAndChangeState();
            super.stateSorted();
            return new EmptyLongStream(this);
        }

        @Override
        public LongStream peek(LongConsumer action) {
            return nextEmptyLongStream(action);
        }

        @Override
        public LongStream limit(long maxSize) {
            if (maxSize < 0)
                throw new IllegalArgumentException(Long.toString(maxSize));
            checkIfOperatedOnOrClosedAndChangeState();
            return new EmptyLongStream(this);
        }

        @Override
        public LongStream skip(long n) {
            if (n < 0)
                throw new IllegalArgumentException(Long.toString(n));
            if (n == 0) return this;
            checkIfOperatedOnOrClosedAndChangeState();
            return new EmptyLongStream(this);
        }

        @Override
        public LongStream takeWhile(LongPredicate predicate) {
            return nextEmptyLongStream(predicate);
        }

        @Override
        public LongStream dropWhile(LongPredicate predicate) {
            return nextEmptyLongStream(predicate);
        }

        @Override
        public void forEach(LongConsumer action) {
            checkStateAndThenParameters(action);
            // do nothing
        }

        @Override
        public void forEachOrdered(LongConsumer action) {
            checkStateAndThenParameters(action);
            // do nothing
        }

        private static final long[] EMPTY_ARRAY = {};

        @Override
        public long[] toArray() {
            checkIfOperatedOnOrClosedAndChangeState();
            return EMPTY_ARRAY;
        }

        @Override
        public long reduce(long identity, LongBinaryOperator op) {
            checkParametersAndThenState(op);
            return identity;
        }

        @Override
        public OptionalLong reduce(LongBinaryOperator op) {
            checkParametersAndThenState(op);
            return OptionalLong.empty();
        }

        @Override
        public <R> R collect(Supplier<R> supplier, ObjLongConsumer<R> accumulator, BiConsumer<R, R> combiner) {
            checkParametersAndThenState(supplier, accumulator, combiner);
            return supplier.get();
        }

        @Override
        public OptionalLong min() {
            checkIfOperatedOnOrClosedAndChangeState();
            return OptionalLong.empty();
        }

        @Override
        public OptionalLong max() {
            checkIfOperatedOnOrClosedAndChangeState();
            return OptionalLong.empty();
        }

        @Override
        public long count() {
            checkIfOperatedOnOrClosedAndChangeState();
            return 0L;
        }

        @Override
        public boolean anyMatch(LongPredicate predicate) {
            checkParametersAndThenState(predicate);
            return false;
        }

        @Override
        public boolean allMatch(LongPredicate predicate) {
            checkParametersAndThenState(predicate);
            return true;
        }

        @Override
        public boolean noneMatch(LongPredicate predicate) {
            checkParametersAndThenState(predicate);
            return true;
        }

        @Override
        public OptionalLong findFirst() {
            checkIfOperatedOnOrClosedAndChangeState();
            return OptionalLong.empty();
        }

        @Override
        public OptionalLong findAny() {
            checkIfOperatedOnOrClosedAndChangeState();
            return OptionalLong.empty();
        }

        private static final PrimitiveIterator.OfLong EMPTY_ITERATOR =
                new PrimitiveIterator.OfLong() {
                    @Override
                    public long nextLong() {
                        throw new NoSuchElementException();
                    }

                    @Override
                    public boolean hasNext() {
                        return false;
                    }
                };


        @Override
        public PrimitiveIterator.OfLong iterator() {
            checkIfOperatedOnOrClosedAndChangeState();
            return EMPTY_ITERATOR;
        }

        @Override
        public Spliterator.OfLong spliterator() {
            checkIfOperatedOnOrClosedAndChangeState();
            return new EmptySpliterator.OfLong(stateBareCharacteristics());
        }

        @Override
        public LongStream sequential() {
            return this;
        }

        @Override
        public LongStream parallel() {
            return StreamSupport.longStream(spliterator(), true);
        }

        @Override
        public LongStream unordered() {
            checkIfOperatedOnOrClosed();
            if (super.unorderedSame()) return this;
            else return new EmptyLongStream(this);
        }

        @Override
        public LongStream onClose(Runnable closeHandler) {
            return StreamSupport.longStream(
                    spliterator(), isParallel()
            ).onClose(closeHandler);
        }

        @Override
        public long sum() {
            checkIfOperatedOnOrClosedAndChangeState();
            return 0;
        }

        @Override
        public OptionalDouble average() {
            checkIfOperatedOnOrClosedAndChangeState();
            return OptionalDouble.empty();
        }

        @Override
        public LongSummaryStatistics summaryStatistics() {
            checkIfOperatedOnOrClosedAndChangeState();
            return new LongSummaryStatistics();
        }

        @Override
        public DoubleStream asDoubleStream() {
            checkIfOperatedOnOrClosedAndChangeState();
            return new EmptyDoubleStream(this);
        }

        @Override
        public Stream<Long> boxed() {
            checkIfOperatedOnOrClosedAndChangeState();
            return new EmptyStream<>(this);
        }
    }

    static final class EmptyDoubleStream extends EmptyBaseStream implements DoubleStream {
        public EmptyDoubleStream(EmptyBaseStream input) {
            super(input);
        }

        public EmptyDoubleStream(Spliterator.OfDouble spliterator) {
            super(spliterator);
        }

        @Override
        public DoubleStream filter(DoublePredicate predicate) {
            return nextEmptyDoubleStream(predicate);
        }

        @Override
        public DoubleStream map(DoubleUnaryOperator mapper) {
            return nextEmptyDoubleStream(mapper);
        }

        @Override
        public <U> Stream<U> mapToObj(DoubleFunction<? extends U> mapper) {
            return nextEmptyStream(mapper);
        }

        @Override
        public IntStream mapToInt(DoubleToIntFunction mapper) {
            return nextEmptyIntStream(mapper);
        }

        @Override
        public LongStream mapToLong(DoubleToLongFunction mapper) {
            return nextEmptyLongStream(mapper);
        }

        @Override
        public DoubleStream flatMap(DoubleFunction<? extends DoubleStream> mapper) {
            return nextEmptyDoubleStream(mapper);
        }

        @Override
        public DoubleStream mapMulti(DoubleMapMultiConsumer mapper) {
            return nextEmptyDoubleStream(mapper);
        }

        @Override
        public DoubleStream distinct() {
            checkIfOperatedOnOrClosedAndChangeState();
            super.stateDistinctPrimitiveStream();
            return new EmptyDoubleStream(this);
        }

        @Override
        public DoubleStream sorted() {
            checkIfOperatedOnOrClosedAndChangeState();
            super.stateSorted();
            return new EmptyDoubleStream(this);
        }

        @Override
        public DoubleStream peek(DoubleConsumer action) {
            checkParametersAndThenState(action);
            return new EmptyDoubleStream(this);
        }

        @Override
        public DoubleStream limit(long maxSize) {
            if (maxSize < 0)
                throw new IllegalArgumentException(Double.toString(maxSize));
            checkIfOperatedOnOrClosedAndChangeState();
            return new EmptyDoubleStream(this);
        }

        @Override
        public DoubleStream skip(long n) {
            if (n < 0)
                throw new IllegalArgumentException(Double.toString(n));
            if (n == 0) return this;
            checkIfOperatedOnOrClosedAndChangeState();
            return new EmptyDoubleStream(this);
        }

        @Override
        public DoubleStream takeWhile(DoublePredicate predicate) {
            return nextEmptyDoubleStream(predicate);
        }

        @Override
        public DoubleStream dropWhile(DoublePredicate predicate) {
            return nextEmptyDoubleStream(predicate);
        }

        @Override
        public void forEach(DoubleConsumer action) {
            checkStateAndThenParameters(action);
            // do nothing
        }

        @Override
        public void forEachOrdered(DoubleConsumer action) {
            checkStateAndThenParameters(action);
            // do nothing
        }

        private static final double[] EMPTY_ARRAY = {};

        @Override
        public double[] toArray() {
            checkIfOperatedOnOrClosedAndChangeState();
            return EMPTY_ARRAY;
        }

        @Override
        public double reduce(double identity, DoubleBinaryOperator op) {
            checkParametersAndThenState(op);
            return identity;
        }

        @Override
        public OptionalDouble reduce(DoubleBinaryOperator op) {
            checkParametersAndThenState(op);
            return OptionalDouble.empty();
        }

        @Override
        public <R> R collect(Supplier<R> supplier, ObjDoubleConsumer<R> accumulator, BiConsumer<R, R> combiner) {
            checkParametersAndThenState(supplier, accumulator, combiner);
            return supplier.get();
        }

        @Override
        public OptionalDouble min() {
            checkIfOperatedOnOrClosedAndChangeState();
            return OptionalDouble.empty();
        }

        @Override
        public OptionalDouble max() {
            checkIfOperatedOnOrClosedAndChangeState();
            return OptionalDouble.empty();
        }

        @Override
        public long count() {
            checkIfOperatedOnOrClosedAndChangeState();
            return 0L;
        }

        @Override
        public boolean anyMatch(DoublePredicate predicate) {
            checkParametersAndThenState(predicate);
            return false;
        }

        @Override
        public boolean allMatch(DoublePredicate predicate) {
            checkParametersAndThenState(predicate);
            return true;
        }

        @Override
        public boolean noneMatch(DoublePredicate predicate) {
            checkParametersAndThenState(predicate);
            return true;
        }

        @Override
        public OptionalDouble findFirst() {
            checkIfOperatedOnOrClosedAndChangeState();
            return OptionalDouble.empty();
        }

        @Override
        public OptionalDouble findAny() {
            checkIfOperatedOnOrClosedAndChangeState();
            return OptionalDouble.empty();
        }

        private static final PrimitiveIterator.OfDouble EMPTY_ITERATOR =
                new PrimitiveIterator.OfDouble() {
                    @Override
                    public double nextDouble() {
                        throw new NoSuchElementException();
                    }

                    @Override
                    public boolean hasNext() {
                        return false;
                    }
                };


        @Override
        public PrimitiveIterator.OfDouble iterator() {
            checkIfOperatedOnOrClosedAndChangeState();
            return EMPTY_ITERATOR;
        }

        @Override
        public Spliterator.OfDouble spliterator() {
            checkIfOperatedOnOrClosedAndChangeState();
            return new EmptySpliterator.OfDouble(stateBareCharacteristics());
        }

        @Override
        public DoubleStream sequential() {
            return this;
        }

        @Override
        public DoubleStream parallel() {
            return StreamSupport.doubleStream(spliterator(), true);
        }

        @Override
        public DoubleStream unordered() {
            checkIfOperatedOnOrClosed();
            if (super.unorderedSame()) return this;
            else return new EmptyDoubleStream(this);
        }

        @Override
        public DoubleStream onClose(Runnable closeHandler) {
            return StreamSupport.doubleStream(
                    spliterator(), isParallel()
            ).onClose(closeHandler);
        }

        @Override
        public double sum() {
            checkIfOperatedOnOrClosedAndChangeState();
            return 0;
        }

        @Override
        public OptionalDouble average() {
            checkIfOperatedOnOrClosedAndChangeState();
            return OptionalDouble.empty();
        }

        @Override
        public DoubleSummaryStatistics summaryStatistics() {
            checkIfOperatedOnOrClosedAndChangeState();
            return new DoubleSummaryStatistics();
        }

        @Override
        public Stream<Double> boxed() {
            checkIfOperatedOnOrClosedAndChangeState();
            return new EmptyStream<>(this);
        }
    }

    private abstract static class EmptySpliterator<T, S extends Spliterator<T>, C> {
        private final int characteristics;

        EmptySpliterator(int characteristics) {
            this.characteristics = characteristics;
        }

        public S trySplit() {
            return null;
        }

        public boolean tryAdvance(C consumer) {
            Objects.requireNonNull(consumer);
            return false;
        }

        public void forEachRemaining(C consumer) {
            Objects.requireNonNull(consumer);
        }

        public long estimateSize() {
            return 0;
        }

        public int characteristics() {
            return characteristics;
        }

        private static final class OfRef<T>
                extends EmptySpliterator<T, Spliterator<T>, Consumer<? super T>>
                implements Spliterator<T> {
            OfRef(int characteristics) {
                super(characteristics);
            }
        }

        private static final class OfRefSorted<T>
                extends EmptySpliterator<T, Spliterator<T>, Consumer<? super T>>
                implements Spliterator<T> {
            private final Comparator<? super T> comparator;

            OfRefSorted(int characteristics, Comparator<? super T> comparator) {
                super(characteristics);
                if (!hasCharacteristics(SORTED))
                    throw new IllegalArgumentException("Spliterator only for SORTED");
                this.comparator = comparator;
            }

            @Override
            public Comparator<? super T> getComparator() {
                return comparator;
            }
        }

        private static final class OfInt
                extends EmptySpliterator<Integer, Spliterator.OfInt, IntConsumer>
                implements Spliterator.OfInt {
            OfInt(int characteristics) {
                super(characteristics);
            }
        }

        private static final class OfLong
                extends EmptySpliterator<Long, Spliterator.OfLong, LongConsumer>
                implements Spliterator.OfLong {
            OfLong(int characteristics) {
                super(characteristics);
            }
        }

        private static final class OfDouble
                extends EmptySpliterator<Double, Spliterator.OfDouble, DoubleConsumer>
                implements Spliterator.OfDouble {
            OfDouble(int characteristics) {
                super(characteristics);
            }
        }
    }
}
