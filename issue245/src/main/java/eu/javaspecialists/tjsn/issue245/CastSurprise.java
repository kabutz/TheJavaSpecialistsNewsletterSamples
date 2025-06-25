package eu.javaspecialists.tjsn.issue245;

public class CastSurprise {
    public static void main(String... args) {
        // case 1:
        char c1 = 'a' + 5;  // compiles
        // case 2:
        char c2 = 'a';
        // c2 = c2 + 5; // does not compile
        // case 3:
        char c3 = 'a';
        c3 += 5; // compiles
    }
}
