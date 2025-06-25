package eu.javaspecialists.tjsn.issue179;

public class CalculatorVarargTest {
    public static void main(String... args) {
        long time = System.currentTimeMillis();
        long grandTotal = 0;
        for (int i = 0; i < 100000; i++) {
            grandTotal += test();
        }
        time = System.currentTimeMillis() - time;
        System.out.println("time = " + time + "ms");
        System.out.println("grandTotal = " + grandTotal);
    }

    private static long test() {
        long total = 0;
        for (int i = 0; i < 10000; i++) {
            CalculatorVarArgs calc = new CalculatorVarArgs();
            total += calc.add(i, i / 2);
        }
        return total;
    }
}
