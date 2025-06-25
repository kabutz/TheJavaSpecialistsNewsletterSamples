package eu.javaspecialists.tjsn.issue110;

public class StrangeLabels {
    public static void main(String[] args) {
        what:
        break what; // not an infinite loop, but pointless code

        what:
        new Runnable() { // we can define the same label twice
            // we cannot access the label from in here
            public void run() {
            }
        }.run();

        what:
        System.out.println("Hello"); // completely pointless
        int i;
        what:
        i = 42; // label on the assignment
    }
}
