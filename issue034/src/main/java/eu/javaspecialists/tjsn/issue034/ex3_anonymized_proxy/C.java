package eu.javaspecialists.tjsn.issue034.ex3_anonymized_proxy;

class C implements A {
    private A a;

    C(A a) {
        this.a = a;
    }

    public void f() {
        /* do something, then */
        a.f();
    }
}