package eu.javaspecialists.tjsn.issue170;

// run with --add-opens java.base/java.lang.ref=ALL-UNNAMED
public class ConditionalFinalizerTest2 {
    public static void main(String[] args) throws Exception {
        MBeanUtil.getFinalizerWatcher();
        ConditionalFinalizerTest1.main(args);
        System.out.println("Done - going to sleep for a minute");
        Thread.sleep(60000);
    }
}
