package eu.javaspecialists.tjsn.issue322;

import java.util.*;
import java.util.concurrent.*;

public class BetterLinkedBlockingDeque<E> extends LinkedBlockingDeque<E> {
    public BetterLinkedBlockingDeque() { }

    public BetterLinkedBlockingDeque(int capacity) {
        super(capacity);
    }

    public BetterLinkedBlockingDeque(Collection<? extends E> c) {
        super(c);
    }

    public void putFirst(E e) throws InterruptedException {
        throwIfInterrupted();
        super.putFirst(e);
    }

    public void putLast(E e) throws InterruptedException {
        throwIfInterrupted();
        super.putLast(e);
    }

    public E takeFirst() throws InterruptedException {
        throwIfInterrupted();
        return super.takeFirst();
    }

    public E takeLast() throws InterruptedException {
        throwIfInterrupted();
        return super.takeLast();
    }

    private static void throwIfInterrupted() throws InterruptedException {
        if (Thread.currentThread().interrupted()) throw new InterruptedException();
    }
}
