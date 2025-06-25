package eu.javaspecialists.tjsn.issue109;

public class NullEqualityStrategy implements EqualityStrategy {
    private Object owner;

    public void setOwner(Object owner) {
        this.owner = owner;
    }

    public int hashCode() {
        return System.identityHashCode(owner);
    }

    public boolean equals(Object obj) {
        return owner == obj;
    }
}
