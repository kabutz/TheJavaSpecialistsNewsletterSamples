package eu.javaspecialists.tjsn.issue157;

public class A2 {
    private final B2 b;

    public A2(B2 b) {
        this.b = b;
    }

    public void run() {
        b.f();
    }
}

