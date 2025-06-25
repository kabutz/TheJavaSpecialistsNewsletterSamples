package eu.javaspecialists.tjsn.issue157;

public class A1 {
    private final B1 b;

    public A1(B1 b) {
        this.b = b;
    }

    public void run() {
        b.f();
    }
}