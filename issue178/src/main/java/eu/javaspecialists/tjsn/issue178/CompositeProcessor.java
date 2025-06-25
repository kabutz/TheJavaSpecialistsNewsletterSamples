package eu.javaspecialists.tjsn.issue178;

import java.util.*;

public class CompositeProcessor<E>
        implements Processor<E> {
    private final List<Processor<E>> processors =
            new ArrayList<Processor<E>>();

    public void add(Processor<E> processor) {
        processors.add(processor);
    }

    public boolean process(E e) {
        for (Processor<E> processor : processors) {
            if (!processor.process(e)) return false;
        }
        return true;
    }
}
