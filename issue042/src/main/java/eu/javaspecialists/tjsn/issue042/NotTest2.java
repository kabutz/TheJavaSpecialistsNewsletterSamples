package eu.javaspecialists.tjsn.issue042;

public class NotTest2 {
    public static void main(String[] args) {
        boolean flag = true;
        long start;
        start = -System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            flag = !flag;
        }
        start += System.currentTimeMillis();
        System.out.println("time for flag = !flag: " + start + "ms");

        start = -System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            flag = flag ? false : true;
        }
        start += System.currentTimeMillis();
        System.out.println("time for flag = flag?false:true: " + start + "ms");

        start = -System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            flag ^= true; // XOR
        }
        start += System.currentTimeMillis();
        System.out.println("time for flag ^= true: " + start + "ms");
    }
}
