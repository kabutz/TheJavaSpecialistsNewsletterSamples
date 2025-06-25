package eu.javaspecialists.tjsn.issue192;

public class ThisEscapeTest {
    public static void main(String... args) {
        EventSource es = new EventSource();
        es.start();
        while (true) {
            new ThisEscape(es);
        }
    }
}
