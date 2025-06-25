package eu.javaspecialists.tjsn.issue222;

public class FindTwins {
    public static void main(String... args) {
        Object obj = new Object();
        Object twin = findTwin(obj);
        System.out.printf("found twin: %s and %s, but == is %b%n",
                obj, twin, obj == twin);
    }

    private static Object findTwin(Object obj) {
        int hash = obj.hashCode();
        Object twin;
        long created = 0;
        do {
            twin = new Object();
            if ((++created & 0xfffffff) == 0) {
                System.out.printf("%,d created%n", created);
            }
        } while (twin.hashCode() != obj.hashCode());
        System.out.printf("We had to create %,d objects%n", created);
        return twin;
    }
}
