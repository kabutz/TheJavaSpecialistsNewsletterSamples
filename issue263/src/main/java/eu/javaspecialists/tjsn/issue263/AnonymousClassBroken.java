package eu.javaspecialists.tjsn.issue263;

public class AnonymousClassBroken {
    public static void main(String... args) {
        Object obj = new Object() {
            private void test() {
                System.out.println("anonymous test");
            }
        };
        // obj.test(); // <-- cannot find symbol
    }
}
