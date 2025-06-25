package eu.javaspecialists.tjsn.issue133;

import com.jamonapi.*;

import java.util.*;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.*;

public class ConverterPerformanceTest {
    private static final int COUNT = 20 * 1000;
    private static final int REPEATS = 10;

    private static final int[] NO_OF_ELEMENTS = {5, 50, 500};

    private static final Converter[] converters = {
            Converter.WITH_COPY, Converter.WITH_LOOP, Converter.TO_ARRAY,
            Converter.UNSAFE
    };

    public static void main(String... args) {
        test(new LinkedList());
        test(new ArrayList());
        test(new HashSet());
        test(new ConcurrentLinkedQueue());
    }

    private static void gcAndWait() {
        try {
            // this not only clears the memory, but offers a specific
            // GC signature that will allow us to determine which GC
            // activity was for which test.
            MILLISECONDS.sleep(500);
            System.gc();
            System.gc();
            System.gc();
            MILLISECONDS.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void test(Collection valueContainer) {
        printHeading(valueContainer);
        for (int elementCount : NO_OF_ELEMENTS) {
            for (Converter converter : converters) {
                test(elementCount, valueContainer, converter);
            }
        }
        System.out.println();
    }

    private static void test(int elements, Collection valueContainer,
                             Converter converter) {
        prepareContainer(elements, valueContainer);
        gcAndWait();

        // make sure that the HotSpot Compiler has chance for its magic
        for (int i = 0; i < COUNT * REPEATS / 2; i++) {
            converter.convert(String.class, valueContainer);
        }

        String measurement = converter.getClass().getSimpleName() +
                "-" + valueContainer.getClass().getSimpleName()
                + "(" + elements + ")";
        Monitor mon = MonitorFactory.start(measurement);

        for (int j = 0; j < REPEATS; j++) {
            mon.start();
            for (int i = 0; i < COUNT; i++) {
                converter.convert(String.class, valueContainer);
            }
            mon.stop();
        }
        printResults(measurement, mon);
    }

    private static void prepareContainer(int elements,
                                         Collection valueContainer) {
        for (int i = 0; i < elements; i++) {
            valueContainer.add(Integer.toString(i));
        }
    }

    public static void printHeading(Collection valueContainer) {
        System.out.println(valueContainer.getClass());
        System.out.println("Avg,Var,Min,Max");
    }

    public static void printResults(String measurement, Monitor mon) {
        System.out.printf("%s,%.0f,%.0f,%.0f,%.0f%n", measurement,
                mon.getAvg(), mon.getStdDev(), mon.getMin(), mon.getMax());
    }
}
