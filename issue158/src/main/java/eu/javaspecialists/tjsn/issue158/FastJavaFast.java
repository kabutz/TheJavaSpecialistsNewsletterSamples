package eu.javaspecialists.tjsn.issue158;

public class FastJavaFast {
    public void test() {
        for (int i = 0; i < 1000 * 1000 * 1000; i++) {
            test2();
        }
    }

    void test2() {
        for (int j = 0; j < 1000 * 1000 * 1000; j++) {
            test3();
        }
    }


    void test3() {
        for (int k = 0; k < 1000 * 1000 * 1000; k++) {
            foo();
        }
    }

    // this is a "volatile" method, in the "C" sense
    public void foo() {
    }

    public static void main(String[] args) throws Exception {
        FastJavaFast fast = new FastJavaFast() {
            // I am overriding the foo() method
            public void foo() {
                // do nothing
            }
        };

        // Running the benchmark
        long time = System.currentTimeMillis();
        fast.foo();
        fast.test3();
        fast.test2();
        fast.test();
        time = System.currentTimeMillis() - time;
        System.out.println("time = " + time);
    }
}
