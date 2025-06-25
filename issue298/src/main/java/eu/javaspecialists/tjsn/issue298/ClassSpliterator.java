package eu.javaspecialists.tjsn.issue298;

import java.util.*;
import java.util.function.*;

public class ClassSpliterator implements Spliterator<Class<?>> {
    private Class<?> nextClass;
    private final Deque<Iterator<Class<?>>> unfinished =
            new ArrayDeque<>();

    public ClassSpliterator(Class<?> root) {
        if (!root.isSealed())
            throw new IllegalArgumentException(root + " not sealed");
        nextClass = root;
        addPermittedSubclasses(root);
    }

    private void addPermittedSubclasses(Class<?> root) {
        Optional.ofNullable(root.getPermittedSubclasses())
                .map(Arrays::asList)
                .map(Iterable::iterator)
                .ifPresent(unfinished::addLast);
    }

    @Override
    public boolean tryAdvance(Consumer<? super Class<?>> action) {
        Objects.requireNonNull(action);
        if (nextClass == null) nextClass = findNextClass();
        if (nextClass == null) return false;
        action.accept(nextClass);
        nextClass = null;
        return true;
    }

    private Class<?> findNextClass() {
        while (!unfinished.isEmpty()) {
            Iterator<Class<?>> iterator = unfinished.peekLast();
            if (iterator.hasNext()) {
                var result = iterator.next();
                addPermittedSubclasses(result);
                return result;
            } else {
                unfinished.removeLast();
            }
        }
        return null;
    }

    @Override
    public Spliterator<Class<?>> trySplit() {
        // never support splitting
        return null;
    }

    @Override
    public long estimateSize() {
        // unknown size
        return Long.MAX_VALUE;
    }

    @Override
    public int characteristics() {
        return IMMUTABLE | NONNULL;
    }
}
