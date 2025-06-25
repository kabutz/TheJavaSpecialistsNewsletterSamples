package eu.javaspecialists.tjsn.issue178;

public class AddProcessor<N extends Number>
        implements Processor<N> {
    private double total = 0;

    public boolean process(N n) {
        total += n.doubleValue();
        return true;
    }

    public double getTotal() {
        return total;
    }

    public void reset() {
        total = 0;
    }
}
