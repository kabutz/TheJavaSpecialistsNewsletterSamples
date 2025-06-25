package eu.javaspecialists.tjsn.issue217;

import java.util.concurrent.atomic.*;

// does not work correctly - we could get upper < lower!
public class NumberRangeAtomicBroken implements NumberRange {
    private final AtomicInteger lower = new AtomicInteger(0);
    private final AtomicInteger upper = new AtomicInteger(0);

    public boolean setLower(int newLower) {
        if (newLower > upper.get()) return false;
        lower.set(newLower);
        return true;
    }

    public boolean setUpper(int newUpper) {
        if (newUpper < lower.get()) return false;
        upper.set(newUpper);
        return true;
    }

    public boolean isInRange(int number) {
        return number >= lower.get() && number <= upper.get();
    }

    public boolean checkCorrectRange() {
        int lower = this.lower.get();
        int upper = this.upper.get();
        return lower <= upper;
    }
}
