package eu.javaspecialists.tjsn.issue158;

public class A5 implements Test {
    private final B5 b;

    public A5(B5 b) {
        this.b = b;
    }

    public void run() {
        b.f();
    }

    public String description() {
        return "Poly-Morphic: Four subclasses, via interface";
    }
}
