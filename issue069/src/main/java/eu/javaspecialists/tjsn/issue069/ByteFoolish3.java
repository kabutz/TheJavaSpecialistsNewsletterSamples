package eu.javaspecialists.tjsn.issue069;

public class ByteFoolish3 {
    public ByteFoolish3() {
        int i = 128;
        byte b = 0;
        b |= i;
        i = 0;
        i |= b;
    }
}
