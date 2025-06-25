package eu.javaspecialists.tjsn.issue226;

public class ThreadFromMainThread {
    public static void main(String... args)
            throws InterruptedException {
        System.setSecurityManager(
                new ThreadWatcher(
                        DemoSupport.createPredicate(),
                        DemoSupport.createConsumer()
                )
        );

        new Thread(DemoSupport.createHelloJob(),
                "This should work 1").start();

        System.setSecurityManager(null);
    }
}
