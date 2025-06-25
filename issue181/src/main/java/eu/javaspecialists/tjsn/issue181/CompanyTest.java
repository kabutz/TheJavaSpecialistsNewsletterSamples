package eu.javaspecialists.tjsn.issue181;

public class CompanyTest {
    public static void main(String... args) {
        Company company = new Company("Cretesoft", 10000.0,
                new MoralFibreImpl());
        company.makeMoney();
        company.damageEnvironment();
        company.becomeFocusOfMediaAttention();
        Company company2 = new Company("Cretesoft2", 20000.0,
                ProxyGenerator.make(MoralFibre.class,
                        MoralFibreImpl.class,
                        Concurrency.NONE));
        company2.makeMoney();
        company2.makeMoney();
        company2.makeMoney();
        company2.damageEnvironment();
        company2.becomeFocusOfMediaAttention();
    }
}