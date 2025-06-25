package eu.javaspecialists.tjsn.issue312;

public class PrintingNumbersWithHash {
    public static void main(String... args) {
        System.out.format("0%o%n", 01234);
        System.out.format("%#o%n", 01234);
        System.out.format("%#x%n", 0xcafebabe);
        System.out.format("%#X%n", 0xcafebabe);
        System.out.format("0x%x%n", 0xcafebabe);
        System.out.format("0x%X%n", 0xcafebabe);
    }
}
