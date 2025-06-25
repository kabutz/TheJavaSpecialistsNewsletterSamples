package eu.javaspecialists.tjsn.issue158;

public class A1 implements Test {
    private final B1 b;

    public A1(B1 b) {
        this.b = b;
    }

    public void run() {
        b.f();
    }

    public String description() {
        return "No-morphic: Single subclass, pointed to directly";
    }
}
