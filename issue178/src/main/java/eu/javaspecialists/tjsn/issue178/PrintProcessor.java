package eu.javaspecialists.tjsn.issue178;

public class PrintProcessor<E> implements Processor<E> {
    public boolean process(E o) {
        System.out.println(">>> " + o);
        return true;
    }
}
