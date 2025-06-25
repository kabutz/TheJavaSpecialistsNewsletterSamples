package eu.javaspecialists.tjsn.issue188;

import org.jpatterns.gof.*;

@StrategyPattern.ConcreteStrategy
public class EmptyInterlockTask implements
        InterlockTask<Integer> {
    public final int upto;
    private volatile int count;

    public EmptyInterlockTask(int upto) {
        this.upto = upto;
    }

    public boolean isDone() {
        return count >= upto;
    }

    public void call() {
        count++;
    }

    public Integer get() {
        return count;
    }

    public void reset() {
        count = 0;
    }
}
