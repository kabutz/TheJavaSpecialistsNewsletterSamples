package eu.javaspecialists.tjsn.issue178;

public class SimpleCollectionTest {
    public static void main(String... args) {
        String[] arr = {"John", "Andre", "Neil", "Heinz", "Anton"};
        SimpleCollection<String> names =
                new SimpleCollection<String>(arr);

        for (String name : names) { // works
            System.out.println(name);
        }

        System.out.println(names); // works

        System.out.println(names.isEmpty()); // works

        names.add("Dirk"); // throws UnsupportedOperationException

        names.remove("Neil"); // throws UnsupportedOperationException
    }
}
