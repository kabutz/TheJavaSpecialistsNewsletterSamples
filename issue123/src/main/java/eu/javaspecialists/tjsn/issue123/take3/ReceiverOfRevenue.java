package eu.javaspecialists.tjsn.issue123.take3;

public class ReceiverOfRevenue {
    public static void main(String... args) {
        TaxPayer heinz = new Employee(TaxPayer.EMPLOYEE, 50000, true,
                Employee.Gender.FEMALE);
        TaxPayer maxsol = new TaxPayer(TaxPayer.COMPANY, 100000);
        TaxPayer family = new TaxPayer(TaxPayer.TRUST, 30000);
        System.out.println(heinz.extortCash());
        System.out.println(maxsol.extortCash());
        System.out.println(family.extortCash());
    }
}