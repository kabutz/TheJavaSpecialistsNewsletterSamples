package eu.javaspecialists.tjsn.issue222;

public class UniqueNumbers {
    public static void main(String... args) {
        for (int i = 0; i < 10; i++) {
            System.out.printf("%,d%n", new Object().hashCode());
        }
    }
}
