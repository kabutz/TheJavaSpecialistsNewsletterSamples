package eu.javaspecialists.tjsn.issue084;

public class Fibonacci {
    public int calculate(int n) {
        if (n < 0)
            throw new IllegalArgumentException("number must be >= 0");
        switch (n) {
            case 0:
                return 0;
            case 1:
                return 1;
            default:
                return calculate(n - 2) + calculate(n - 1);
        }
    }
}
