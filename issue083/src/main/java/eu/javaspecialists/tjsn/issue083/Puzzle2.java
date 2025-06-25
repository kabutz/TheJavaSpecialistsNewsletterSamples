package eu.javaspecialists.tjsn.issue083;

public class Puzzle2 {
    private int i = 1;

    public int test() {
        try {
            return i;
        } finally {
            i = 2;
        }
    }

    public static void main(String[] args) {
        Puzzle2 p = new Puzzle2();
        System.out.println(p.test());
    }
}
