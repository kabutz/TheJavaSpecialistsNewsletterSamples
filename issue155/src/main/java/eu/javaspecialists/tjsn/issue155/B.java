package eu.javaspecialists.tjsn.issue155;

public class B {
    private String WRITE_LOCK_OBJECT = "WRITE_LOCK_OBJECT";

    public static void main(String... args) {
        B b = new B();
        A a = new A();
        a.compareLocks(b.WRITE_LOCK_OBJECT);
    }
}
