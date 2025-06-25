package eu.javaspecialists.tjsn.issue066;

public class TryCatchCostTest extends TryCatch {
    public static void main(String[] args) {
        Integer i = new Integer(3);
        Boolean b = new Boolean(true);
        long TEST_DURATION = 2 * 1000;
        boolean res;
        long stop;

        stop = TEST_DURATION + System.currentTimeMillis();
        int test1_i = 0;
        // we do not want to test with every increment, otherwise the
        // call to System.currentTimeMillis() will dwarf the rest of
        // our calls.
        while (((test1_i % 1000) != 0)
                || (System.currentTimeMillis() < stop)) {
            test1_i++;
            res = test1(i);
        }
        System.out.println("test1(i) executed " + test1_i + " times");

        stop = TEST_DURATION + System.currentTimeMillis();
        int test1_b = 0;
        while (((test1_b % 1000) != 0)
                || (System.currentTimeMillis() < stop)) {
            test1_b++;
            res = test1(b);
        }
        System.out.println("test1(b) executed " + test1_b + " times");
        System.out.println("test1(i) was " + (test1_i / test1_b)
                + " times faster");

        stop = TEST_DURATION + System.currentTimeMillis();
        int test2_i = 0;
        while (((test2_i % 1000) != 0)
                || (System.currentTimeMillis() < stop)) {
            test2_i++;
            res = test2(i);
        }
        System.out.println("test2(i) executed " + test2_i + " times");

        stop = TEST_DURATION + System.currentTimeMillis();
        int test2_b = 0;
        while (((test2_b % 1000) != 0)
                || (System.currentTimeMillis() < stop)) {
            test2_b++;
            res = test2(b);
        }
        System.out.println("test2(b) executed " + test2_b + " times");
    }
}
