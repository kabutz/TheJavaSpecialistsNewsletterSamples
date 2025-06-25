package eu.javaspecialists.tjsn.issue155;

public class Amdahl {
    private static double amdahl(double f, int n) {
        return 1.0 / (f + (1 - f) / n);
    }

    public static void main(String... args) {
        for (int i = 1; i < 10000; i *= 3) {
            System.out.println("amdahl(0.1, " + i + ") = " + amdahl(0.1, i));
        }
    }
}
