package eu.javaspecialists.tjsn.issue123.take3;

public class Employee extends TaxPayer {
    public enum Gender {MALE, FEMALE}

    private final boolean married;
    private final Gender gender;

    public Employee(TaxStrategy strategy, double income,
                    boolean married, Gender gender) {
        super(strategy, income);
        this.married = married;
        this.gender = gender;
    }

    public boolean isMarried() {
        return married;
    }

    public Gender getGender() {
        return gender;
    }
}
