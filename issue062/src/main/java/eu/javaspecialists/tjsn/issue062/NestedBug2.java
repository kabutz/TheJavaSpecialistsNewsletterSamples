package eu.javaspecialists.tjsn.issue062;

public class NestedBug2 {
    private Integer wings = new Integer(2);

    public NestedBug2() {
        new ComplexBug();
    }

    private class ComplexBug extends Insect {
        public void printDetails() {
            if (wings != null) { // line 8
                System.out.println(wings);
            }
        }
    }

    public static void main(String[] arguments) {
        new NestedBug2();
    }
}
