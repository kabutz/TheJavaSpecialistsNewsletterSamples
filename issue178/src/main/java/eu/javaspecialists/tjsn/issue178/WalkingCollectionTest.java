package eu.javaspecialists.tjsn.issue178;

public class WalkingCollectionTest {
    public static void main(String... args) {
        WalkingCollection<Long> ages = new WalkingCollection<Long>(
                new java.util.ArrayList<Long>()
        );

        ages.add(10L);
        ages.add(35L);
        ages.add(12L);
        ages.add(33L);

        PrintProcessor<Long> pp = new PrintProcessor<Long>();
        ages.iterate(pp);

        AddProcessor<Long> ap = new AddProcessor<Long>();
        ages.iterate(ap);
        System.out.println("ap.getTotal() = " + ap.getTotal());

        // composite
        System.out.println("Testing Composite");
        ap.reset();

        CompositeProcessor<Long> composite =
                new CompositeProcessor<Long>();
        composite.add(new Processor<Long>() {
            private long previous = Long.MIN_VALUE;

            public boolean process(Long current) {
                boolean result = current >= previous;
                previous = current;
                return result;
            }
        });
        composite.add(ap);
        composite.add(pp);
        ages.iterate(composite);
        System.out.println("ap.getTotal() = " + ap.getTotal());
    }
}
