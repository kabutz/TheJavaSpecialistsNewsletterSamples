package eu.javaspecialists.tjsn.issue179;

public class CalculatorVarArgs {
    public int add(int... is) {
        if (is.length == 0) throw new IllegalArgumentException();
        if (is.length == 1) return is[0];
        if (is.length == 2) return is[0] + is[1];
        if (is.length == 3) return is[0] + is[1] + is[2];
        if (is.length == 4) return is[0] + is[1] + is[2] + is[3];
        int total = 0;
        for (int i : is) {
            total += i;
        }
        return total;
    }
}
