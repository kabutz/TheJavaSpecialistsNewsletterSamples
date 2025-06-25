package eu.javaspecialists.tjsn.issue263;

public class AnonymousClassVar {
    public static void main(String... args) {
        var obj = new Object() {
            private void test() {
                System.out.println("anonymous test");
            }
        };
        obj.test(); // works!
    }
}
