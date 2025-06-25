package eu.javaspecialists.tjsn.issue115;

// 3,693,312,257
public class ObjectCreationTest2 extends CreateTest {
    public void run() {
        Object[] pool = new Object[1 * 1024 * 1024];
        long count = 0;
        while (true) {
            pool[(int)((count++) % pool.length)] = new Object();
            incCount();
        }
    }
}