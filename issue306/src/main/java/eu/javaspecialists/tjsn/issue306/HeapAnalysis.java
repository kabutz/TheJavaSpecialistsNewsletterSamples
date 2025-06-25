package eu.javaspecialists.tjsn.issue306;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.netbeans.lib.profiler.heap.HeapFactory;
import org.netbeans.lib.profiler.heap.Instance;
import org.netbeans.lib.profiler.heap.PrimitiveArrayInstance;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_16LE;

public class HeapAnalysis {
    private enum Coder {LATIN1, UTF16}
    private record StringData(Coder coder, int length) {}

    public static void main(String... args) throws IOException {
        if (args.length < 1 || args.length > 2 ||
                args.length > 1 && !args[0].equals("-verbose")) {
            System.err.println("Usage: java HeapAnalysis " +
                    "[-verbose] heapdump");
            System.exit(1);
        }
        var verbose = args.length == 2;
        var filename = args[args.length - 1];
        System.out.println("Inspecting heap file " + filename);
        var heap = HeapFactory.createHeap(new File(filename));
        var stringClass = heap.getJavaClassByName(
                "java.lang.String");
        var instances = stringClass.getInstancesIterator();
        var stats = extractStringData(instances, verbose);
        printStatistics(stats);
    }

    private static List<StringData> extractStringData(
            Iterator<Instance> instances, boolean verbose) {
        var result = new ArrayList<StringData>();
        while (instances.hasNext()) {
            Instance instance = instances.next();
            Coder coder = getCoder(instance);
            int length = getLength(instance, coder, verbose);
            result.add(new StringData(coder, length));
        }
        return result;
    }

    private static Coder getCoder(Instance instance) {
        Byte coder = (Byte) instance.getValueOfField("coder");
        return switch (coder) {
            case 0 -> Coder.LATIN1;
            case 1 -> Coder.UTF16;
            case null -> throw new IllegalStateException(
                    "Analysis for Java 11+ heap dumps only -"
                            + " field coder not found in"
                            + " java.lang.String");
            default -> throw new IllegalStateException(
                    "Unknown coder: " + coder);
        };
    }

    private static int getLength(Instance instance, Coder coder,
                                 boolean verbose) {
        var array = (PrimitiveArrayInstance)
                instance.getValueOfField("value");
        if (array == null)
            throw new IllegalStateException(
                    "java.lang.String instances did not have a"
                            + " value array field");

        int length = array.getLength();

        if (verbose) {
            List<String> arrayValues = array.getValues();
            byte[] bytes = new byte[length];
            int i = 0;
            for (String str : arrayValues)
                bytes[i++] = Byte.parseByte(str);
            System.out.println(switch (coder) {
                case LATIN1 -> "LATIN1: "
                        + new String(bytes, ISO_8859_1);
                case UTF16 -> "UTF16: "
                        + new String(bytes, UTF_16LE);
            });
        }
        return length;
    }

    private static final Predicate<StringData> LATIN1_FILTER =
            datum -> datum.coder() == Coder.LATIN1;
    private static final Predicate<StringData> UTF16_FILTER =
            datum -> datum.coder() == Coder.UTF16;

    private static void printStatistics(List<StringData> data) {
        long j8Memory = memoryUsed(data.stream(), 2);
        long j11MemoryLatin1 =
                memoryUsed(data.stream().filter(LATIN1_FILTER), 1);
        long j11MemoryUTF16 =
                memoryUsed(data.stream().filter(UTF16_FILTER), 2);
        long j11Memory = j11MemoryLatin1 + j11MemoryUTF16;
        var latin1Size = data.stream().filter(LATIN1_FILTER).count();
        var utf16Size = data.stream().filter(UTF16_FILTER).count();
        System.out.printf(Locale.US, """
                        Total number of String instances:
                            LATIN1 %,d
                            UTF16  %,d
                            Total  %,d
                        """,
                latin1Size, utf16Size, latin1Size + utf16Size);
        System.out.printf(Locale.US, """
                Java 8 memory used by String instances:
                    Total  %,d bytes
                """, j8Memory);
        System.out.printf(Locale.US, """
                Java 11+ memory used by String instances:
                    LATIN1 %,d bytes
                    UTF16  %,d bytes
                    Total  %,d bytes
                """, j11MemoryLatin1, j11MemoryUTF16, j11Memory);
        System.out.printf(Locale.US, "Saving of %.2f%%%n", 100.0 *
                (j8Memory - j11Memory) / j8Memory);
    }

    private static int memoryUsed(Stream<StringData> stats,
                                  int bytesPerChar) {
        return stats
                .mapToInt(datum -> getStringSize(datum.length(),
                        bytesPerChar))
                .sum();
    }

    private static int getStringSize(int length, int bytesPerChar) {
        return 24 + 16 +
                (int) (Math.ceil(length * bytesPerChar / 8.0) * 8);
    }
}
