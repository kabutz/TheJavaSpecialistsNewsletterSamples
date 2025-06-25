package eu.javaspecialists.tjsn.issue123.take4;

public class ReceiverOfRevenue {
    public static void main(String... args) {
        TaxPayer heinz = new Employee(TaxPayer.EMPLOYEE, 50000, true,
                Employee.Gender.FEMALE);
        TaxPayer maxsol = new Company(TaxPayer.COMPANY, 100000, 2);
        TaxPayer family = new Trust(TaxPayer.TRUST, 30000, false);
        System.out.println(heinz.extortCash());
        System.out.println(maxsol.extortCash());
        System.out.println(family.extortCash());
    }
}