package eu.javaspecialists.tjsn.issue043;

import sun.misc.*;

/**
 * The idea for this code came from James Pereira, this code
 * looks quite different to the well-structured, logically
 * named class he sent me.
 * The idea is that we register a handler for a Ctrl+C
 * signal and then handle it.
 */
public class Aaarggh {
    public static void main(String[] args) throws Exception {
        Signal.handle(new Signal("INT"), new SignalHandler() {
            public void handle(Signal sig) {
                System.out.println(
                        "Aaarggh, a user is trying to interrupt me!!");
                System.out.println(
                        "(throw garlic at user, say `shoo, go away')");
            }
        });
        for (int i = 0; i < 100; i++) {
            Thread.sleep(1000);
            System.out.print('.');
        }
    }
}
