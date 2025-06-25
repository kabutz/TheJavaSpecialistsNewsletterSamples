package eu.javaspecialists.tjsn.issue256;

import java.util.*;

public class ObjectChurner {
    // 4GB heap, 2048 regions, 2mb each
    // humongous objects: 10*1mb, 5*2mb, 3*10mb, 1*50mb
    // largish objects: 10_000*100kb, 10_000*10kb, 100_000*1kb
    // smaller objects: 100_000*100, 100_000*10, 100_000*1
    private final int[] sizes;

    private int nextPos = 0;
    private final byte[][] data = new byte[1 * 1024 * 1024][];

    public ObjectChurner() {
        List<Integer> sizes = new ArrayList<>();
        // humongous objects
        add(sizes, 10, 1024 * 1024);
        add(sizes, 5, 2 * 1024 * 1024);
        add(sizes, 3, 10 * 1024 * 1024);
        add(sizes, 1, 50 * 1024 * 1024);

        // largish objects:
        add(sizes, 10_000, 100 * 1024);
        add(sizes, 10_000, 10 * 1024);
        add(sizes, 100_000, 1 * 1024);

        // largish objects:
        add(sizes, 100_000, 100);
        add(sizes, 100_000, 10);
        add(sizes, 100_000, 1);

        Collections.shuffle(sizes, new Random(0));
        this.sizes = sizes.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }

    private void add(List<Integer> sizes, int number, int size) {
        for (int i = 0; i < number; i++) {
            sizes.add(size);
        }
    }

    private void churn() {
        for (int i = 0; i < 10; i++) {
            for (int size : sizes) {
                data[nextPos++ & (data.length - 1)] = new byte[size];
            }
            System.out.println("Next cycle");
        }
    }

    public static void main(String... args) {
        ObjectChurner churner = new ObjectChurner();
        churner.churn();
    }
}
