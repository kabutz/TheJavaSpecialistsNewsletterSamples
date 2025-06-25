package eu.javaspecialists.tjsn.issue044;

public class BetterDoubleTest {
    public static void main(String[] args) {
        System.out.println("Floating-point machine parameters");
        System.out.println("---------------------------------");
        System.out.println("radix = " + DhbMath.getRadix());
        System.out.println("Machine precision = " +
                DhbMath.getMachinePrecision());
        System.out.println("Default numerical precision = " +
                DhbMath.defaultNumericalPrecision());
        System.out.println(DhbMath.equals(
                2.71828182845905,
                (2.71828182845904 + 0.00000000000001)));
        System.out.println(DhbMath.equals(
                2.71828182845905, 2.71828182845904));
        System.out.println(DhbMath.equals(
                2.718281828454, 2.718281828455));
        System.out.println(DhbMath.equals(
                2.7182814, 2.7182815));
    }
}
