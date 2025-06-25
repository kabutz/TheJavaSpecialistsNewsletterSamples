package eu.javaspecialists.tjsn.issue271;

import java.util.concurrent.*;

public class NamedThreadFactory implements ThreadFactory {
    private String name;
    private int count;

    public NamedThreadFactory(String name) {
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        count++;
        Thread t = new Thread(r, name + "-" + count);
        System.out.println("New thread " + t);
        return t;
    }
}
