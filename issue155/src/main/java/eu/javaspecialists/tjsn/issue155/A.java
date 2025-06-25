package eu.javaspecialists.tjsn.issue155;

public class A {
    private String WRITE_LOCK_OBJECT = "WRITE_LOCK_OBJECT";

    public void compareLocks(Object other) {
        if (other == WRITE_LOCK_OBJECT) {
            System.out.println("they are identical!");
            System.out.println(
                    System.identityHashCode(WRITE_LOCK_OBJECT));
            System.out.println(
                    System.identityHashCode(other));
        } else {
            System.out.println("they do not match");
        }
    }

    public static void main(String... args) {
        A a1 = new A();
        A a2 = new A();
        a1.compareLocks(a2.WRITE_LOCK_OBJECT);
    }
}
