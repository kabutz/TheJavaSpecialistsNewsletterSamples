package eu.javaspecialists.tjsn.issue123.take2;

public class EmployeeTaxStrategy implements TaxStrategy {
    private static final double RATE = 0.45;

    public double extortCash(double income) {
        return income * RATE;
    }
}