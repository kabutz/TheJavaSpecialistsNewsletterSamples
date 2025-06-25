package eu.javaspecialists.tjsn.issue158;

public class A4 implements Test {
    private final B4 b;

    public A4(B4 b) {
        this.b = b;
    }

    public void run() {
        b.f();
    }

    public String description() {
        return "Poly-Morphic: Three subclasses, via interface";
    }
}
