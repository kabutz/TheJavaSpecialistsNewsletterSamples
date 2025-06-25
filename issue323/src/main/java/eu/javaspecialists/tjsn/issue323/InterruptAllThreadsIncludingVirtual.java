package eu.javaspecialists.tjsn.issue323;

import java.util.concurrent.*;

// Compile and run with --add-exports java.base/jdk.internal.vm=ALL-UNNAMED
public class InterruptAllThreadsIncludingVirtual {
    public static void main(String... args) throws Exception {
        var interrupter = new ThreadableVisitor() {
            public void visit(Thread thread) {
                thread.interrupt();
            }
        };

        var sleepingVirtualThread = Thread.ofVirtual()
                .name("Sleeping Virtual Thread").start(() -> {
                    try {
                        Thread.sleep(10_000);
                    } catch (InterruptedException e) {
                        System.out.println("Virtual thread sleep interrupted");
                    }
                });
        try (var fixedPool = Executors.newFixedThreadPool(2);
             var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            fixedPool.submit(() -> {
                try {
                    Thread.sleep(10_000);
                } catch (InterruptedException e) {
                    System.out.println("Fixed thread sleep interrupted");
                }
                return "done";
            });
            scope.fork(() -> {
                try {
                    Thread.sleep(10_000);
                } catch (InterruptedException e) {
                    System.out.println("Forked sleep interrupted");
                }
                return "done";
            });
            scope.fork(() -> {
                try (var inner = new StructuredTaskScope.ShutdownOnFailure()) {
                    inner.fork(() -> {
                        try {
                            Thread.sleep(10_000);
                        } catch (InterruptedException e) {
                            System.out.println("Inner forked sleep interrupted");
                        }
                        return "done";
                    });
                    try {
                        inner.join().throwIfFailed();
                    } catch (InterruptedException e) {
                        System.out.println("Inner scope interrupted");
                    }
                    return "done";
                }
            });
            Thread.sleep(100);
            Threadables.create().accept(interrupter);
            System.out.println("All threads interrupted");

            Thread.interrupted(); // clear my interrupt
            scope.join().throwIfFailed();
            sleepingVirtualThread.join();
        }
    }
}
