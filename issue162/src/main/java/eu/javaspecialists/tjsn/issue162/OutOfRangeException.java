package eu.javaspecialists.tjsn.issue162;

public class OutOfRangeException
        extends IllegalArgumentException {
    private final long value, min, max;

    public OutOfRangeException(long value, long min, long max) {
        super("Value " + value + " out of range " +
                "[" + min + ".." + max + "]");
        this.value = value;
        this.min = min;
        this.max = max;
    }

    public long getValue() {
        return value;
    }

    public long getMin() {
        return min;
    }

    public long getMax() {
        return max;
    }
}
