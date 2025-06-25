package eu.javaspecialists.tjsn.issue029;

public class SixtyFourBooleanFactory implements ObjectFactory {
    private static class SixtyFourBooleans {
        boolean a0, a1, a2, a3, a4, a5, a6, a7;
        boolean b0, b1, b2, b3, b4, b5, b6, b7;
        boolean c0, c1, c2, c3, c4, c5, c6, c7;
        boolean d0, d1, d2, d3, d4, d5, d6, d7;
        boolean e0, e1, e2, e3, e4, e5, e6, e7;
        boolean f0, f1, f2, f3, f4, f5, f6, f7;
        boolean g0, g1, g2, g3, g4, g5, g6, g7;
        boolean h0, h1, h2, h3, h4, h5, h6, h7;
    }

    public Object makeObject() {
        return new SixtyFourBooleans();
    }
}