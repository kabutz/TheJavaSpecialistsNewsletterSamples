package eu.javaspecialists.tjsn.issue123.take4;


public class Trust extends TaxPayer<Trust> {
    private final boolean disability;

    public Trust(TaxStrategy<Trust> strategy, double income, boolean disability) {
        super(strategy, income);
        this.disability = disability;
    }

    protected Trust getDetailedType() {
        return this;
    }

    public boolean isDisability() {
        return disability;
    }
}
