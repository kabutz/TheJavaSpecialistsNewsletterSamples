package eu.javaspecialists.tjsn.issue115;

// 23,713,415,087
public class ObjectCreationTest1 extends CreateTest {
    public void run() {
        while (true) {
            new Object();
            incCount();
        }
    }
}