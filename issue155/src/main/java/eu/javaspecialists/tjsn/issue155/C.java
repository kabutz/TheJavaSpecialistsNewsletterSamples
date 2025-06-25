package eu.javaspecialists.tjsn.issue155;

public class C {
    private String WRITE_LOCK_OBJECT =
            new String("WRITE_LOCK_OBJECT");

    public static void main(String... args) {
        C c = new C();
        A a = new A();
        a.compareLocks(c.WRITE_LOCK_OBJECT);
        a.compareLocks(c.WRITE_LOCK_OBJECT.intern());
    }
}
