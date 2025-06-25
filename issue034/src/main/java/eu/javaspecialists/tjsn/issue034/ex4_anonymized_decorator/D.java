package eu.javaspecialists.tjsn.issue034.ex4_anonymized_decorator;

class D extends C {
    D(A a) {
        super(a);
    }

    public void g() {
    /* decorate the other operation in some way, then call the
     other operation() */
        f();
    }
}