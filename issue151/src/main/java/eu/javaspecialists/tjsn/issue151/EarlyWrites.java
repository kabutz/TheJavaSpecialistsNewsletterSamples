package eu.javaspecialists.tjsn.issue151;

public class EarlyWrites {
    private int x;
    private int y;

    public void f() {
        int a = x;
        y = 3;
    }

    public void g() {
        int b = y;
        x = 4;
    }
}
