package eu.javaspecialists.tjsn.issue009;

public class Polyseme {
    public static class Top {
        public void f(Object o) {
            System.out.println("Top.f(Object)");
        }

        public void f(String s) {
            System.out.println("Top.f(String)");
        }
    }

    public static void main(String[] args) {
        Top top = new Top();
        top.f(new java.util.Vector());
        top.f("hello");
        top.f((Object) "bye");
    }
}
