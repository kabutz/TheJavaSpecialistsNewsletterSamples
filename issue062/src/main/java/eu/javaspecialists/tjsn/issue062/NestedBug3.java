package eu.javaspecialists.tjsn.issue062;

public class NestedBug3 {
    private Integer wings = new Integer(2);

    public NestedBug3() {
        new ComplexBug();
    }

    private class ComplexBug extends Insect {
        ComplexBug() {
            System.out.println("Inside ComplexBug Constructor");
        }

        public void printDetails() {
            System.out.println(wings);
        }
    }

    public static void main(String[] arguments) {
        new NestedBug3();
    }
}
