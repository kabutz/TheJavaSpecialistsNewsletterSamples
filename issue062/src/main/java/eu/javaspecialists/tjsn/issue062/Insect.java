package eu.javaspecialists.tjsn.issue062;

public abstract class Insect {
    public Insect() {
        System.out.println("Inside Insect() Constructor");
        printDetails();
    }

    public void printDetails() {
        System.out.println("Just an insect");
    }
}