package eu.javaspecialists.tjsn.issue094;

public class MyClass {
    private boolean b = true; // unnecessary initialisation
    private int i = 42;       // unnecessary initialisation

    public MyClass(boolean b, int i) {
        this.b = b;
        this.i = i;
    }
}
