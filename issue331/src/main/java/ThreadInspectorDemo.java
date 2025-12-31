import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

// Use the JVM args --add-opens java.base/java.lang=ALL-UNNAMED
public class ThreadInspectorDemo {
    public static void main(String... args)
            throws InterruptedException, IOException {
        demo(Thread.ofVirtual());
        demo(Thread.ofPlatform());
    }

    private static void demo(Thread.Builder builder)
            throws InterruptedException, IOException {
        System.out.println(builder.getClass().getSimpleName());

        demoUnstarted(builder);
        demoTerminated(builder);
        demoRunning(builder);
        demoWaiting(builder);
        demoBlocked(builder);
        demoSleeping(builder);
        demoWaitingOnIO(builder);

        System.out.println();
    }

    private static void demoUnstarted(Thread.Builder builder) {
        demo("Unstarted", builder.unstarted(() -> {}));
    }

    private static void demoTerminated(Thread.Builder builder)
            throws InterruptedException {
        var terminatedThread = builder.start(() -> {});
        terminatedThread.join();
        demo("Terminated", terminatedThread);
    }

    private static void demoRunning(Thread.Builder builder)
            throws InterruptedException {
        var running = new AtomicBoolean(true);
        var runningThread = builder.start(() -> {
            while (running.get()) ;
        });
        Thread.sleep(10); // give thread a chance to really start
        demo("Running", runningThread);
        running.set(false);
        runningThread.join();
    }

    private static void demoWaiting(Thread.Builder builder)
            throws InterruptedException {
        var monitor = new Object();
        var waitingThread = builder.start(() -> {
            synchronized (monitor) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    throw new CancellationException();
                }
            }
        });
        Thread.sleep(10);
        demo("Waiting", waitingThread);
        synchronized (monitor) {
            monitor.notify();
        }
        waitingThread.join();
    }

    private static void demoBlocked(Thread.Builder builder)
            throws InterruptedException {
        var monitor = new Object();
        synchronized (monitor) {
            var blockedThread = builder.start(() -> {
                synchronized (monitor) {}
            });
            Thread.sleep(10);
            demo("Blocked", blockedThread);
        }
    }

    private static void demoSleeping(Thread.Builder builder)
            throws InterruptedException {
        var sleepingThread = builder.start(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new CancellationException();
            }
        });
        Thread.sleep(10);
        demo("Sleeping", sleepingThread);
    }

    private static void demoWaitingOnIO(Thread.Builder builder)
            throws InterruptedException, IOException {
        var waitingOnIOThread = builder.start(() -> {
            try (var serverSocket = new ServerSocket(8080)) {
                serverSocket.accept();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
        Thread.sleep(100); // needs longer wait
        demo("Waiting on IO", waitingOnIOThread);
        new Socket("localhost", 8080);
        waitingOnIOThread.join();
    }

    private static void demo(String description, Thread thread) {
        System.out.printf("%s: %s%n", description,
                ThreadInspector.getCompoundThreadStates(thread));
    }
}
