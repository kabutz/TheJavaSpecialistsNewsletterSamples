package eu.javaspecialists.tjsn.issue123.take3;

public class CompanyTaxStrategy implements TaxStrategy {
    private static final double RATE = 0.30;

    public double extortCash(TaxPayer p) {
        return p.getIncome() * RATE;
    }
}