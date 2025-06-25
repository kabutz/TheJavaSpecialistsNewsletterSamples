package eu.javaspecialists.tjsn.issue188;

import org.jpatterns.gof.*;

import java.util.*;
import java.util.concurrent.atomic.*;

@StrategyPattern.ConcreteStrategy
public class InterleavedNumberTestingStrategy implements
        InterlockTask<VerifyResult> {
    public final int upto;
    private final Map<Integer, Thread> numbers =
            new LinkedHashMap<Integer, Thread>();
    private final AtomicInteger count = new AtomicInteger(0);

    public InterleavedNumberTestingStrategy(int upto) {
        this.upto = upto;
    }

    public boolean isDone() {
        return count.get() >= upto;
    }

    public void call() {
        int next = count.getAndIncrement();
        numbers.put(next, Thread.currentThread());
    }

    public VerifyResult get() {
        if (numbers.size() < upto) {
            return new VerifyResult("Only " + numbers.size() +
                    " numbers were entered");
        }
        Object previous = null;
        int i = 0;
        for (Map.Entry<Integer, Thread> entry : numbers.entrySet()) {
            if (i != entry.getKey()) {
                return new VerifyResult("numbers out of sequence");
            }
            if (entry.getValue() == previous) {
                return new VerifyResult("Did not alternate threads");
            }
            previous = entry.getValue();
            i++;
        }
        Set<Thread> values = new HashSet<Thread>(numbers.values());
        if (values.size() != 2) {
            return new VerifyResult(
                    "More than two threads were inserting values");
        }
        return new VerifyResult();
    }

    public void reset() {
        numbers.clear();
        count.set(0);
    }
}
