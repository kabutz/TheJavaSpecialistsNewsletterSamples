package eu.javaspecialists.tjsn.issue124;

public class CompleteTest {
    private static final int RUNS = 10;
    private static final long TEST_TIME = 100;

    public static void main(String... args) throws Exception {
        test(1);
        test(10);
        test(100);
        test(1000);
        test(10000);
        test(100000);
    }

    private static void test(int length) {
        PerformanceHarness harness = new PerformanceHarness();
        Average arrayClone = harness.calculatePerf(
                new PerformanceChecker(TEST_TIME,
                        new ArrayCloneTest(length)), RUNS);
        Average arrayNewAndCopy = harness.calculatePerf(
                new PerformanceChecker(TEST_TIME,
                        new ArrayNewAndCopyTest(length)), RUNS);

        System.out.println("Length=" + length);
        System.out.printf("Clone %.0f\t%.0f%n",
                arrayClone.mean(), arrayClone.stddev());
        System.out.printf("Copy  %.0f\t%.0f%n",
                arrayNewAndCopy.mean(), arrayNewAndCopy.stddev());
        System.out.printf("Diff  %.2fx%n",
                arrayNewAndCopy.mean() / arrayClone.mean());
        System.out.println();
    }
}
