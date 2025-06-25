package eu.javaspecialists.tjsn.issue083;

public class Puzzle1 {
    public int test() {
        int i = 1;
        try {
            return i;
        } finally {
            i = 2;
        }
    }

    public static void main(String[] args) {
        Puzzle1 p = new Puzzle1();
        System.out.println(p.test());
    }
}
