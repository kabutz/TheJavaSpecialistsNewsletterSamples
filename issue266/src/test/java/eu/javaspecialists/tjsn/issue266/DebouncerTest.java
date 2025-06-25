package eu.javaspecialists.tjsn.issue266;

import org.junit.jupiter.api.*;

import java.util.concurrent.atomic.*;

import static org.junit.jupiter.api.Assertions.*;

public class DebouncerTest {
    private Debouncer<Runnable> makeDebouncer() {
        // Create Debouncer that only runs once a second
        throw new UnsupportedOperationException("TODO");
    }

    @Test
    public void testDebouncer() throws InterruptedException {
        Debouncer<Runnable> db = makeDebouncer();
        AtomicInteger rx_count = new AtomicInteger();
        AtomicInteger ry_count = new AtomicInteger();

        Runnable rx = () -> {
            System.out.println("x");
            rx_count.incrementAndGet();
        };
        Runnable ry = () -> {
            System.out.println("y");
            ry_count.incrementAndGet();
        };

        for (int i = 0; i < 8; i++) {
            Thread.sleep(50);
            db.call(rx);
            Thread.sleep(50);
            db.call(ry);
        }
        Thread.sleep(200); // expecting x and y
        assertEquals(1, rx_count.get());
        assertEquals(1, ry_count.get());

        for (int i = 0; i < 10000; i++) {
            db.call(rx);
        }
        Thread.sleep(2_400); // expecting only x
        assertEquals(2, rx_count.get());
        assertEquals(1, ry_count.get());

        db.call(ry);
        Thread.sleep(1_100); // expecting only y
        assertEquals(2, rx_count.get());
        assertEquals(2, ry_count.get());
        db.shutdown();
    }

    @Test
    public void testManyRunnables() throws InterruptedException {
        Debouncer<Runnable> db = makeDebouncer();
        LongAdder counter = new LongAdder();
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            db.call(() -> {
                System.out.println("i=" + finalI);
                counter.increment();
            });
        }
        Thread.sleep(2500);
        assertEquals(100, counter.longValue());
        db.shutdown();
    }
}