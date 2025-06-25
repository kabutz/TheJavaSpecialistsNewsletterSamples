package eu.javaspecialists.tjsn.issue181;

public class Company {
    private final String name;
    private final MoralFibre moralFibre;
    private double cash;

    public Company(String name, double cash, MoralFibre moralFibre) {
        this.name = name;
        this.cash = cash;
        this.moralFibre = moralFibre;
        System.out.println("Company constructed: " + this);
    }

    public void damageEnvironment() {
        cash += 4000000;
        System.out.println("Company.damageEnvironment(): " + this);
    }

    public void makeMoney() {
        cash += 1000000;
        System.out.println("Company.makeMoney(): " + this);
    }

    public void becomeFocusOfMediaAttention() {
        cash -= moralFibre.actSociallyResponsibly();
        cash -= moralFibre.cleanupEnvironment();
        cash -= moralFibre.empowerEmployees();
        System.out.println("Look how good we are... " + this);
    }

    public String toString() {
        return String.format("%s has $ %.2f", name, cash);
    }
}