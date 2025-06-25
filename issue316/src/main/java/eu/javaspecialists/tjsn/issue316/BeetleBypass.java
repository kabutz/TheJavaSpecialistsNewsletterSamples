package eu.javaspecialists.tjsn.issue316;

public class BeetleBypass extends Insect {
    private static final ThreadLocal<Integer> TEMP_LEGS =
            new ThreadLocal<>();
    private final int legs;

    public BeetleBypass(int legs) {
        saveLegsTemp(legs);
        super();
        System.out.println("Inside Beetle() Constructor");
        this.legs = legs;
        TEMP_LEGS.remove();
    }

    private static void saveLegsTemp(int legs) {
        TEMP_LEGS.set(legs);
    }

    public void printDetails() {
        System.out.println(STR."The beetle has \{legs()} legs");
        if (legs() < 6) {
            System.out.println("Ouch");
        }
    }

    private int legs() {
        if (TEMP_LEGS.get() != null) return TEMP_LEGS.get();
        return legs;
    }
}
