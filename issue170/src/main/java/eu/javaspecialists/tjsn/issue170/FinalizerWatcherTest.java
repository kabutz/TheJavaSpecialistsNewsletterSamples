package eu.javaspecialists.tjsn.issue170;

// run with --add-opens java.base/java.lang.ref=ALL-UNNAMED
public class FinalizerWatcherTest {
    public static void main(String[] args)
            throws InterruptedException {
        MBeanUtil.getFinalizerWatcher();

        while (true) {
            A a = new A();
            a = null;
            Thread.currentThread().sleep(1000);
        }
    }

    public static class A {
        private boolean val;

        protected void finalize() throws Throwable {
            super.finalize();
            System.out.println("A.finalize");
        }
    }
}
