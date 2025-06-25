package eu.javaspecialists.tjsn.issue009;

public class BreadthFirst {
    public static class Top {
        public void f(Object o) {
            System.out.println("Top.f(Object)");
        }
    }

    public static class Middle extends Top {
        public void f(String s) {
            System.out.println("Middle.f(String)");
        }
    }

    public static void main(String[] args) {
        Top top = new Middle();
        top.f(new java.util.Vector());
        top.f("hello");
        top.f((Object) "bye");
    }
}
