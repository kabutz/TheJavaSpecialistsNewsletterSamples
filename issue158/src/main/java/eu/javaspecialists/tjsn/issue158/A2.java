package eu.javaspecialists.tjsn.issue158;


public class A2 implements Test {
    private final B2 b;

    public A2(B2 b) {
        this.b = b;
    }

    public void run() {
        b.f();
    }

    public String description() {
        return "Mono-Morphic: Single subclass, via interface";
    }
}
