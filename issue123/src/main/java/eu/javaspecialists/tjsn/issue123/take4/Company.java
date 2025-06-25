package eu.javaspecialists.tjsn.issue123.take4;

public class Company extends TaxPayer<Company> {
    private final int numberOfEmployees;

    public Company(TaxStrategy<Company> strategy, double income,
                   int numberOfEmployees) {
        super(strategy, income);
        this.numberOfEmployees = numberOfEmployees;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    protected Company getDetailedType() {
        return this;
    }
}