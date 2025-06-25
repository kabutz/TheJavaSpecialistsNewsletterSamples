package eu.javaspecialists.tjsn.issue123.take4;

public class EmployeeTaxStrategy implements TaxStrategy<Employee> {
    private static final double NORMAL_RATE = 0.40;
    private static final double MARRIED_FEMALE_RATE = 0.48;

    public double extortCash(Employee e) {
        if (e.isMarried() &&
                e.getGender() == Employee.Gender.FEMALE) {
            return e.getIncome() * MARRIED_FEMALE_RATE;
        }
        return e.getIncome() * NORMAL_RATE;
    }
}
