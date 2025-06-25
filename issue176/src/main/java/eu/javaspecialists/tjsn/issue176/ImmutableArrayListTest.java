package eu.javaspecialists.tjsn.issue176;

public class ImmutableArrayListTest {
    public static void main(String... args) {
        ImmutableArrayList<String> ial =
                new ImmutableArrayList<String>();
        ial = ial.add("Heinz").add("Max").add("Kabutz");
        for (Object obj : ial) {
            System.out.println(obj);
        }
    }
}
