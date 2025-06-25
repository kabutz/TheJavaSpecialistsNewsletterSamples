package eu.javaspecialists.tjsn.issue123.take1;

public class ReceiverOfRevenue {
    public static void main(String... args) {
        TaxPayer heinz = new TaxPayer(TaxPayer.EMPLOYEE, 50000);
        TaxPayer maxsol = new TaxPayer(TaxPayer.COMPANY, 100000);
        TaxPayer family = new TaxPayer(TaxPayer.TRUST, 30000);
        System.out.println(heinz.extortCash());
        System.out.println(maxsol.extortCash());
        System.out.println(family.extortCash());
    }
}