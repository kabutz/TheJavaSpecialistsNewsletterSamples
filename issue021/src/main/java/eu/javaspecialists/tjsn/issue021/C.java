package eu.javaspecialists.tjsn.issue021;

public class C {
    public static void main(String[] args) {
        A a = new A();
        a.f();
        ((A) new B()).f();
        B b = new B();
        b.f();
    }
}