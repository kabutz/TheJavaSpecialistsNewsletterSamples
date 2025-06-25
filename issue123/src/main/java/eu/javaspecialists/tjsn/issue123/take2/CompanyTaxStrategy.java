package eu.javaspecialists.tjsn.issue123.take2;

public class CompanyTaxStrategy implements TaxStrategy {
    private static final double RATE = 0.30;

    public double extortCash(double income) {
        return income * RATE;
    }
}