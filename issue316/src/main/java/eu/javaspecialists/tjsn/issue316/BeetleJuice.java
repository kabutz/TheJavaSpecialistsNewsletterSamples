package eu.javaspecialists.tjsn.issue316;

public class BeetleJuice extends Insect {
    private final int legs;

    public BeetleJuice(int legs) {
        System.out.println("Going to call super() now");
        System.out.println("legs = " + legs);
        // this.legs = legs; // not allowed
        super();
        System.out.println("Inside Beetle() Constructor");
        this.legs = legs;
    }

    public void printDetails() {
        System.out.println("The beetle has " + legs + " legs");
        if (legs < 6) {
            System.out.println("Ouch");
        }
    }
}
