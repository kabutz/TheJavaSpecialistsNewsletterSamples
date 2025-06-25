package eu.javaspecialists.tjsn.issue108;

public class BetterArrayListTest {
    public static void main(String... args) {
        BetterArrayList<String> names =
                new BetterArrayList<String>(String.class);
        names.add("Wolfgang");
        names.add("Leander");
        names.add("Klaus");
        names.add("Reinhard");
        String[] nameArray = names.toArray();
        for (String s : nameArray) {
            System.out.println(s);
        }
    }
}
