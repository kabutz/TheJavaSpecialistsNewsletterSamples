package eu.javaspecialists.tjsn.issue177;

public class PrintingDateFast {
    public static void main(String... args) {
        long time = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            System.out.println(System.currentTimeMillis() + " log message");
        }
        time = System.currentTimeMillis() - time;
        System.out.println("time = " + time);
    }
}
