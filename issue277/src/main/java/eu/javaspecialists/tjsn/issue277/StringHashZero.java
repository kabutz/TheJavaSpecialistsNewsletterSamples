package eu.javaspecialists.tjsn.issue277;

public class StringHashZero {
    public static void main(String... args) {
        String s = "ARcZguv";
        System.out.println(s.hashCode());
        System.out.println((s + s).hashCode());
        System.out.println((s + s + s).hashCode());
        System.out.println((s + s + s + s).hashCode());
    }
}
