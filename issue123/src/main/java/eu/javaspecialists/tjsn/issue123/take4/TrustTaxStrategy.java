package eu.javaspecialists.tjsn.issue123.take4;


public class TrustTaxStrategy implements TaxStrategy<Trust> {
    private static final double RATE = 0.40;

    public double extortCash(Trust e) {
        if (e.isDisability()) {
            return 0;
        }
        return e.getIncome() * RATE;
    }
}
