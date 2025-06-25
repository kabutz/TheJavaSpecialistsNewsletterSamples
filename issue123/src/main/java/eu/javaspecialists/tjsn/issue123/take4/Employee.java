package eu.javaspecialists.tjsn.issue123.take4;

public class Employee extends TaxPayer<Employee> {
    public enum Gender {MALE, FEMALE}

    private final boolean married;
    private final Gender gender;

    public Employee(TaxStrategy<Employee> strategy, double income,
                    boolean married, Gender gender) {
        super(strategy, income);
        this.married = married;
        this.gender = gender;
    }

    protected Employee getDetailedType() {
        return this;
    }

    public boolean isMarried() {
        return married;
    }

    public Gender getGender() {
        return gender;
    }
}
