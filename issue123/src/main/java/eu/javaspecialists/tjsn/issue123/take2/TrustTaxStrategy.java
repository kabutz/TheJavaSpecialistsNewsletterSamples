package eu.javaspecialists.tjsn.issue123.take2;


public class TrustTaxStrategy implements TaxStrategy {
    private static final double RATE = 0.40;

    public double extortCash(double income) {
        return income * RATE;
    }
}
