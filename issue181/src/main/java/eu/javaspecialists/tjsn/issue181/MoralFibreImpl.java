package eu.javaspecialists.tjsn.issue181;

public class MoralFibreImpl implements MoralFibre {
    // very expensive to create moral fibre!
    private byte[] costOfMoralFibre = new byte[900 * 1000];

    {
        System.out.println("Moral Fibre Created!");
    }

    // AIDS orphans
    public double actSociallyResponsibly() {
        return costOfMoralFibre.length / 3;
    }

    // shares to employees
    public double empowerEmployees() {
        return costOfMoralFibre.length / 3;
    }

    // oiled sea birds
    public double cleanupEnvironment() {
        return costOfMoralFibre.length / 3;
    }
}
