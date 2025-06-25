package eu.javaspecialists.tjsn.issue158;

public class A6 implements Test {
    private final B6 b;

    public A6(B6 b) {
        this.b = b;
    }

    public void run() {
        b.f();
    }

    public String description() {
        return "Poly-Morphic: Eight subclasses, via interface";
    }
}
