package eu.javaspecialists.tjsn.issue109;

public interface EqualityStrategy {
    /**
     * Sets the owner object of the strategy.  This will be the
     * object for which the hashCode() needs to be calculated and
     * which will be compared through equals().
     */
    void setOwner(Object o);

    /**
     * Calculates the hashcode for the owner object.
     */
    int hashCode();

    /**
     * Compares the owner object to other.
     */
    boolean equals(Object other);
}
