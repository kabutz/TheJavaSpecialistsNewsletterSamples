package eu.javaspecialists.tjsn.issue134;

public class CompositeKey {
    private String key1, key2;

    public CompositeKey(String key1, String key2) {
        this.key1 = key1;
        this.key2 = key2;
    }

    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompositeKey)) return false;

        CompositeKey that = (CompositeKey) o;
        if (key1 != null ? !key1.equals(that.key1) : that.key1 != null)
            return false;
        if (key2 != null ? !key2.equals(that.key2) : that.key2 != null)
            return false;
        return true;
    }

    public final int hashCode() {
        int result;
        result = (key1 != null ? key1.hashCode() : 0);
        result = 31 * result + (key2 != null ? key2.hashCode() : 0);
        return result;
    }

    public String getKey1() {
        return key1;
    }

    public String getKey2() {
        return key2;
    }
}
