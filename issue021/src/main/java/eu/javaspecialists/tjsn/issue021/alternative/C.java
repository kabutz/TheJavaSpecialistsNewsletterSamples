package eu.javaspecialists.tjsn.issue021.alternative;

public class C {
    public static void main(String[] args) {
        A a = new A();
        a.f(a);
        a = new B();
        a.f(a);
        B b = new B();
        b.f(b);
    }
}