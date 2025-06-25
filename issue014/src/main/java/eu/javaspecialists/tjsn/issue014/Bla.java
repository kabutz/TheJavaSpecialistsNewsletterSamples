package eu.javaspecialists.tjsn.issue014;

public class Bla {
    private char[] c1 = "hello".toCharArray();
    private final char[] c2 = "bye".toCharArray();

    public String toString() {
        return c1 + ", " + c2;
    }
}