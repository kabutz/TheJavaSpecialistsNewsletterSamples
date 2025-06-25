package eu.javaspecialists.tjsn.issue229;

import java.text.*;
import java.util.concurrent.*;
import java.util.logging.*;

public class ThreadPoolExecutorExtTest {
    private static final Logger LOG = Logger.getLogger(
            ThreadPoolExecutorExtTest.class.getName()
    );

    private static final ThreadLocal<DateFormat> df =
            new ThreadLocal<DateFormat>() {
                protected DateFormat initialValue() {
                    return new SimpleDateFormat("yyyy-MM-dd");
                }
            };

    public static void main(String... args)
            throws InterruptedException {
        ThreadPoolExecutor tpe = new ThreadPoolExecutorExt(
                1, 1, 0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                (m, t, tl, v) -> {
                    LOG.warning(
                            () -> String.format(
                                    "Thread %s %s ThreadLocal %s with value %s%n",
                                    t, m, tl.getClass(), v)
                    );
                }
        );

        for (int i = 0; i < 10; i++) {
            tpe.submit(() ->
                    System.out.println(System.identityHashCode(df.get())));
            Thread.sleep(1000);
        }
        tpe.shutdown();
    }
}
