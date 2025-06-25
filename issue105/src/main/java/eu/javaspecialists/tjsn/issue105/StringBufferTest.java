package eu.javaspecialists.tjsn.issue105;

public class StringBufferTest {
    private static final int UPTO = 10 * 1000;
    private final int repeats;

    public StringBufferTest(int repeats) {
        this.repeats = repeats;
    }

    private long testNewBufferDefault() {
        long time = System.currentTimeMillis();
        for (int i = 0; i < repeats; i++) {
            StringBuffer buf = new StringBuffer();
            for (int j = 0; j < UPTO; j++) {
                buf.append(j);
            }
            buf.toString();
        }
        time = System.currentTimeMillis() - time;
        return time;
    }

    private long testNewBufferCorrectSize() {
        long time = System.currentTimeMillis();
        for (int i = 0; i < repeats; i++) {
            StringBuffer buf = new StringBuffer(38890);
            for (int j = 0; j < UPTO; j++) {
                buf.append(j);
            }
            buf.toString();
        }
        time = System.currentTimeMillis() - time;
        return time;
    }

    private long testExistingBuffer() {
        StringBuffer buf = new StringBuffer();
        long time = System.currentTimeMillis();
        for (int i = 0; i < repeats; i++) {
            buf.setLength(0);
            for (int j = 0; j < UPTO; j++) {
                buf.append(j);
            }
            buf.toString();
        }
        time = System.currentTimeMillis() - time;
        return time;
    }

    public String testAll() {
        return testNewBufferDefault() + "," +
                testNewBufferCorrectSize() + "," + testExistingBuffer();
    }

    public static void main(String[] args) {
        System.out.print(System.getProperty("java.version") + ",");
        System.out.print(System.getProperty("java.vm.name") + ",");
        // warm up the hotspot compiler
        new StringBufferTest(10).testAll();
        System.out.println(new StringBufferTest(400).testAll());
    }
}
