package eu.javaspecialists.tjsn.issue170;

public class ConditionalFinalizerTest1 {
    public static void main(String[] args)
            throws InterruptedException {
        long time = System.currentTimeMillis();
        for (int i = 0; i < 10 * 1000 * 1000; i++) {
            ConditionalFinalizer cf = new ConditionalFinalizer(i);
            if (i % (1000 * 1000) != 0) {
                cf.close();
            }
        }
        time = System.currentTimeMillis() - time;
        System.out.println("time = " + time);
    }
}
