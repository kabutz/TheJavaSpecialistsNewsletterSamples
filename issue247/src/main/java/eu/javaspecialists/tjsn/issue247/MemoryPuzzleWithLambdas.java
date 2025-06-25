package eu.javaspecialists.tjsn.issue247;

import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.*;

public class MemoryPuzzleWithLambdas {
    static class A {
        public Runnable get() {
            return new Runnable() {
                public void run() {
                    System.out.println("Hi from A");
                }
            };
        }

        protected void finalize() throws Throwable {
            System.out.println("A finalized");
        }
    }

    static class B {
        public Runnable get() {
            return () -> System.out.println("Hi from B");
        }

        protected void finalize() throws Throwable {
            System.out.println("B finalized");
        }
    }

    static class C {
        private int count = 0;

        public Runnable get() {
            return () -> System.out.println("Hi from C #" + ++count);
        }

        protected void finalize() throws Throwable {
            System.out.println("C finalized");
        }
    }

    static class D {
        private static int count = 0;

        public Runnable get() {
            return () -> System.out.println("Hi from D #" + ++count);
        }

        protected void finalize() throws Throwable {
            System.out.println("D finalized");
        }
    }

    static class E {
        private int count = ThreadLocalRandom.current().nextInt();

        public Runnable get() {
            int count = this.count;
            return () -> System.out.println("Hi from E " + count);
        }

        protected void finalize() throws Throwable {
            System.out.println("E finalized");
        }
    }

    static class F {
        public Runnable get() {
            return this::friendly;
        }

        public void friendly() {
            System.out.println("Hi from F");
        }

        protected void finalize() throws Throwable {
            System.out.println("F finalized");
        }
    }

    static class G { // Thanks Gray Watson for the suggestion
        public Runnable get() {
            return new MyRunnable();
        }

        protected void finalize() {
            System.out.println("G finalized");
        }

        private static class MyRunnable implements Runnable {
            public void run() {
                System.out.println("Hi from G");
            }
        }
    }

    public static void main(String... args) {
        ScheduledExecutorService timer =
                Executors.newSingleThreadScheduledExecutor();
        timer.scheduleAtFixedRate(new A().get(), 1, 1, SECONDS);
        timer.scheduleAtFixedRate(new B().get(), 1, 1, SECONDS);
        timer.scheduleAtFixedRate(new C().get(), 1, 1, SECONDS);
        timer.scheduleAtFixedRate(new D().get(), 1, 1, SECONDS);
        timer.scheduleAtFixedRate(new E().get(), 1, 1, SECONDS);
        timer.scheduleAtFixedRate(new F().get(), 1, 1, SECONDS);
        timer.scheduleAtFixedRate(new G().get(), 1, 1, SECONDS);
        timer.scheduleAtFixedRate(System::gc, 1, 1, SECONDS);
    }
}
