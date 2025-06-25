package eu.javaspecialists.tjsn.issue157;

public class A3 {
    private final B3 b;

    public A3(B3 b) {
        this.b = b;
    }

    public void run() {
        b.f();
    }
}