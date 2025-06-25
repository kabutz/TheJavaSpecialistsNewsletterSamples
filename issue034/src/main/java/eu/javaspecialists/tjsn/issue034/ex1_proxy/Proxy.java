package eu.javaspecialists.tjsn.issue034.ex1_proxy;

class Proxy implements Subject {
    private Subject realSubject;

    Proxy(Subject realSubject) {
        this.realSubject = realSubject;
    }

    public void request() {
        /* do something, then */
        realSubject.request();
    }
}