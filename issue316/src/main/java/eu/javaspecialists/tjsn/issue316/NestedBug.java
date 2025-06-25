package eu.javaspecialists.tjsn.issue316;

public class NestedBug {
    private Integer wings = new Integer(2);

    public NestedBug() {
        new ComplexBug();
    }

    private class ComplexBug extends Insect {
        public void printDetails() {
            System.out.println(wings);
        }
    }

    public static void main(String... args) {
        new NestedBug();
    }
}
