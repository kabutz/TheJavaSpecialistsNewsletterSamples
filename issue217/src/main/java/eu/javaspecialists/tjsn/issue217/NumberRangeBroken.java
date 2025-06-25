package eu.javaspecialists.tjsn.issue217;

// does not work correctly - we could get upper < lower!
public class NumberRangeBroken implements NumberRange {
    private volatile int lower = 0;
    private volatile int upper = 0;

    public boolean setLower(int newLower) {
        if (newLower > upper) return false;
        lower = newLower;
        return true;
    }

    public boolean setUpper(int newUpper) {
        if (newUpper < lower) return false;
        upper = newUpper;
        return true;
    }

    public boolean isInRange(int number) {
        return number >= lower && number <= upper;
    }

    public boolean checkCorrectRange() {
        return lower <= upper;
    }
}