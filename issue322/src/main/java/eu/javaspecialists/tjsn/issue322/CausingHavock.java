package eu.javaspecialists.tjsn.issue322;

public class CausingHavock {
    public static void main(String... args) {
        Thread.getAllStackTraces().keySet().forEach(Thread::interrupt);
    }
}
