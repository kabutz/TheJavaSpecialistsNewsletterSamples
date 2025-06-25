package eu.javaspecialists.tjsn.issue217;

public class NumberRangeSynchronized implements NumberRange {
    // INVARIANT: lower <= upper
    private int lower = 0;
    private int upper = 0;

    public synchronized boolean setLower(int newLower) {
        if (newLower > upper) return false;
        lower = newLower;
        return true;
    }

    public synchronized boolean setUpper(int newUpper) {
        if (newUpper < lower) return false;
        upper = newUpper;
        return true;
    }

    public synchronized boolean isInRange(int number) {
        return number >= lower && number <= upper;
    }

    public synchronized boolean checkCorrectRange() {
        return lower <= upper;
    }
}
