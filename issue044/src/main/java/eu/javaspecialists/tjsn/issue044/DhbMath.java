package eu.javaspecialists.tjsn.issue044;

/**
 * This class determines the parameters of the floating point
 * representation
 * HK: I have refactored it somewhat to make it thread-safe and
 * to make it easier to understand and to fit into my newsletter.
 * The algorithms are the same as in the book.
 *
 * @author Didier H. Besset
 */
public final class DhbMath {
    /**
     * Radix used by floating-point numbers.
     */
    private static final int radix = computeRadix();
    /**
     * Largest positive value which, when added to 1.0, yields 0
     */
    private static final double machinePrecision =
            computeMachinePrecision();
    /**
     * Typical meaningful precision for numerical calculations.
     */
    private static final double defaultNumericalPrecision =
            Math.sqrt(machinePrecision);

    private static int computeRadix() {
        int radix = 0;
        double a = 1.0d;
        double tmp1, tmp2;
        do {
            a += a;
            tmp1 = a + 1.0d;
            tmp2 = tmp1 - a;
        } while (tmp2 - 1.0d != 0.0d);
        double b = 1.0d;
        while (radix == 0) {
            b += b;
            tmp1 = a + b;
            radix = (int) (tmp1 - a);
        }
        return radix;
    }

    private static double computeMachinePrecision() {
        double floatingRadix = getRadix();
        double inverseRadix = 1.0d / floatingRadix;
        double machinePrecision = 1.0d;
        double tmp = 1.0d + machinePrecision;
        while (tmp - 1.0d != 0.0d) {
            machinePrecision *= inverseRadix;
            tmp = 1.0d + machinePrecision;
        }
        return machinePrecision;
    }

    public static int getRadix() {
        return radix;
    }

    public static double getMachinePrecision() {
        return machinePrecision;
    }

    public static double defaultNumericalPrecision() {
        return defaultNumericalPrecision;
    }

    /**
     * @return true if the difference between a and b is less than
     * the default numerical precision
     */
    public static boolean equals(double a, double b) {
        return equals(a, b, defaultNumericalPrecision());
    }

    /**
     * @return true if the relative difference between a and b is
     * less than precision
     */
    public static boolean equals(double a, double b, double precision) {
        double norm = Math.max(Math.abs(a), Math.abs(b));
        return norm < precision || Math.abs(a - b) < precision * norm;
    }
}
