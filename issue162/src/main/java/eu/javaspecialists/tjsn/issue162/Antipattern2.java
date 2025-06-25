package eu.javaspecialists.tjsn.issue162;

public class Antipattern2 {
    private final int val1;
    private final String val2;
    private final long val3;

    public Antipattern2(int val1, String val2, long val3) {
        this.val1 = val1;
        this.val2 = val2;
        this.val3 = val3;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        try {
            Antipattern2 that = (Antipattern2) o;
            return val1 == that.val1
                    && val3 == that.val3
                    && (val2 == that.val2 || val2.equals(that.val2));
        } catch (ClassCastException ex) {
            return false;
        } catch (NullPointerException ex) {
            return false;
        }
    }

    public int hashCode() {
        int result;
        result = val1;
        result = 31 * result + val2.hashCode();
        result = 31 * result + (int) (val3 ^ (val3 >>> 32));
        return result;
    }
}