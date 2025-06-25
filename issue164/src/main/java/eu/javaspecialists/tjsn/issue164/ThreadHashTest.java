package eu.javaspecialists.tjsn.issue164;

public class ThreadHashTest {
    public static void main(String... args) {
        long l1 = (long) ((1L << 31) * (Math.sqrt(5) - 1));
        System.out.println("as 32 bit unsigned: " + l1);
        int i1 = (int) l1;
        System.out.println("as 32 bit signed:   " + i1);
        System.out.println("MAGIC = " + 0x61c88647);
    }
}
