package eu.javaspecialists.tjsn.issue178;

public class WalkingCollectionBrokenTest {
    public static void main(String... args) {
        final WalkingCollection<String> names =
                new WalkingCollection<String>(
                        new java.util.ArrayList<String>()
                );

        names.add("Maximilian");
        names.add("Constance");
        names.add("Priscilla");
        names.add("Evangeline");

        Processor<String> pp = new Processor<String>() {
            public boolean process(String s) {
                if ("Priscilla".equals(s)) names.remove(s);
                return true;
            }
        };
        names.iterate(pp);
    }
}
