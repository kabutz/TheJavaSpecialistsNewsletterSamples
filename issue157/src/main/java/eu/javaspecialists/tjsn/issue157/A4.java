package eu.javaspecialists.tjsn.issue157;

public class A4 {
    private final B4 b;

    public A4(B4 b) {
        this.b = b;
    }

    public void run() {
        b.f();
    }
}