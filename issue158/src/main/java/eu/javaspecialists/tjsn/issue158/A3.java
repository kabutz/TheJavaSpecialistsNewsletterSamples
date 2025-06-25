package eu.javaspecialists.tjsn.issue158;

public class A3 implements Test {
    private final B3 b;

    public A3(B3 b) {
        this.b = b;
    }

    public void run() {
        b.f();
    }

    public String description() {
        return "Bi-Morphic: Two subclasses, via interface";
    }
}
