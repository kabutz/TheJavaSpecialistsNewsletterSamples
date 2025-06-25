package eu.javaspecialists.tjsn.issue105;

public class SingletonTest {
    private static final int UPTO = 100 * 1000 * 1000;

    public static void main(String[] args) {
        System.out.print(System.getProperty("java.version") + ",");
        System.out.print(System.getProperty("java.vm.name") + ",");

        long time;

        time = System.currentTimeMillis();
        for (int i = 0; i < UPTO; i++) {
            Singleton1.getInstance();
        }
        time = System.currentTimeMillis() - time;
        System.out.print(time + ",");

        time = System.currentTimeMillis();
        for (int i = 0; i < UPTO; i++) {
            Singleton2.getInstance();
        }
        time = System.currentTimeMillis() - time;
        System.out.print(time + ",");

        time = System.currentTimeMillis();
        for (int i = 0; i < UPTO; i++) {
            Singleton3.getInstance();
        }
        time = System.currentTimeMillis() - time;
        System.out.println(time);
    }
}
