package eu.javaspecialists.tjsn.issue162;

public class Antipattern1 {
    public static void main(String[] args) {
        try {
            int i = 0;
            while (true) {
                System.out.println(args[i++]);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            // we are done
        }
    }
}
