package eu.javaspecialists.tjsn.issue042;

public class Not {
    public static void test() {
        boolean flag = true;
        long start;

        start = -System.currentTimeMillis();
        for (int i = 0; i < 1000000000; i++) {
            flag ^= true;
        }
        start += System.currentTimeMillis();
        System.out.println("time for flag ^= true: " + start + "ms");

        start = -System.currentTimeMillis();
        for (int i = 0; i < 1000000000; i++) {
            flag = !flag;
        }
        start += System.currentTimeMillis();
        System.out.println("time for flag = !flag: " + start + "ms");

        start = -System.currentTimeMillis();
        for (int i = 0; i < 1000000000; i++) {
            flag = flag ? false : true;
        }
        start += System.currentTimeMillis();
        System.out.println("time for flag = flag?false:true: " + start + "ms");
    }

    public static void main(String[] args) throws Exception {
        test();
        Thread.sleep(1);
        test();
    }
}
