package eu.javaspecialists.tjsn.issue024;

public class SwappingOverFIFOQueueTest {
    private static int ENTRIES;

    public static void test(FIFO queue) {
        for (int i = 0; i < ENTRIES; i++) {
            queue.add(new Object());
        }
        long up_to = System.currentTimeMillis() + 2000;
        int iterations = 0;
        while (System.currentTimeMillis() <= up_to) {
            queue.add(new Object());
            queue.remove();
            iterations++;
        }
        System.out.println(queue.getClass());
        System.out.println("\t" + iterations + " iterations");
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println(
                    "Usage: java SwappingOverFIFOQueueTest entries");
            System.exit(1);
        }
        ENTRIES = Integer.parseInt(args[0]);
        System.out.println("Entries = " + ENTRIES);
        test(new FIFOArrayList());
        test(new FIFOLinkedList());
        SwappingOverFIFOQueue q = new SwappingOverFIFOQueue();
        test(q);
        System.out.println("Current queue implementation " +
                q.getListType().getName());
    }
}
