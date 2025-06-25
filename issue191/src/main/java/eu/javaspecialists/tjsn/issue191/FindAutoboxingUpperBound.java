package eu.javaspecialists.tjsn.issue191;

public class FindAutoboxingUpperBound {
    public static void main(String... args) {
        int i = 0;
        while (isSame(i, i)) {
            i++;
        }
        System.out.println("Upper bound is " + (i - 1));
        i = 0;
        while (isSame(i, i)) {
            i--;
        }
        System.out.println("Lower bound is " + (i + 1));
    }

    private static boolean isSame(Integer i0, Integer i1) {
        return i0 == i1;
    }
}
