package eu.javaspecialists.tjsn.issue263;

public class AnonymousClass {
    public static void main(String... args) {
        new Object() {
            private void test() {
                System.out.println("anonymous test");
            }
        }.test();
    }
}
