package eu.javaspecialists.tjsn.issue029;

public class ThirtyTwoBooleanFactory implements ObjectFactory {
    private static class ThirtyTwoBooleans {
        boolean a0, a1, a2, a3, a4, a5, a6, a7;
        boolean b0, b1, b2, b3, b4, b5, b6, b7;
        boolean c0, c1, c2, c3, c4, c5, c6, c7;
        boolean d0, d1, d2, d3, d4, d5, d6, d7;
    }

    public Object makeObject() {
        return new ThirtyTwoBooleans();
    }
}
