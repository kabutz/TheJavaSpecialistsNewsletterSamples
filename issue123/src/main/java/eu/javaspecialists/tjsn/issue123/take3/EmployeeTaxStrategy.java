package eu.javaspecialists.tjsn.issue123.take3;

public class EmployeeTaxStrategy implements TaxStrategy {
    private static final double NORMAL_RATE = 0.40;
    private static final double MARRIED_FEMALE_RATE = 0.48;

    public double extortCash(TaxPayer p) {
        Employee e = (Employee) p; // here we need to downcast!!!
        if (e.isMarried() &&
                e.getGender() == Employee.Gender.FEMALE) {
            return e.getIncome() * MARRIED_FEMALE_RATE;
        }
        return e.getIncome() * NORMAL_RATE;
    }
}