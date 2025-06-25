package eu.javaspecialists.tjsn.issue109;

public class CachedEqualityStrategy
        implements EqualityStrategy {
    private int hashCode = 0;
    private final EqualityStrategy strat;

    public CachedEqualityStrategy(EqualityStrategy strat) {
        this.strat = strat;
    }

    public int hashCode() {
        if (hashCode == 0) {
            hashCode = strat.hashCode();
        }
        return hashCode;
    }

    public boolean equals(Object obj) {
        return obj != null && (hashCode() == obj.hashCode())
                && strat.equals(obj);
    }

    public void setOwner(Object o) {
        strat.setOwner(o);
        hashCode = 0;
    }
}
