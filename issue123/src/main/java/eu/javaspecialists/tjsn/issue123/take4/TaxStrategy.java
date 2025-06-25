package eu.javaspecialists.tjsn.issue123.take4;

public interface TaxStrategy<P extends TaxPayer> {
    public double extortCash(P p);
}
