package eu.javaspecialists.tjsn.issue063;

public class PerfTest {
    private final CallDetective cd;

    public PerfTest(CallDetective cd) {
        this.cd = cd;
    }

    public void test() {
        f(5);
        f(10);
        f(20);
    }

    public void f(int depth) {
        // build up a big call stack...
        if (depth > 0) {
            f(depth - 1);
        } else {
            long time = -System.currentTimeMillis();
            for (int i = 0; i < 10000; i++) {
                cd.findCaller(0);
            }
            time += System.currentTimeMillis();
            System.out.println(time + "ms");
        }
    }

    public static void main(String[] args) {
        System.out.println("JDK 1.3 approach to find caller:");
        new PerfTest(new CallDetective1_3()).test();
        System.out.println("JDK 1.4 approach to find caller:");
        new PerfTest(new CallDetective1_4()).test();
        // now let's do it again...
        System.out.println("JDK 1.3 approach to find caller:");
        new PerfTest(new CallDetective1_3()).test();
        System.out.println("JDK 1.4 approach to find caller:");
        new PerfTest(new CallDetective1_4()).test();
    }
}
