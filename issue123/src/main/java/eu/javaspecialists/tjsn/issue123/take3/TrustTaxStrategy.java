package eu.javaspecialists.tjsn.issue123.take3;

public class TrustTaxStrategy implements TaxStrategy {
    private static final double RATE = 0.40;

    public double extortCash(TaxPayer p) {
        return p.getIncome() * RATE;
    }
}