package eu.javaspecialists.tjsn.issue218;

import java.util.concurrent.*;

public class RandomArrayFiller {
    public static void main(String... args) {
        int[] numbers = new int[100_000_000];

        for (int i = 0; i < 5; i++) {
            // thread confined
            long time = System.currentTimeMillis();
            fillRandomThreadConfined(numbers);
            time = System.currentTimeMillis() - time;
            System.out.println("Thread Confined took " + time + " ms");

            // stack confined
            time = System.currentTimeMillis();
            fillRandomStackConfined(numbers);
            time = System.currentTimeMillis() - time;
            System.out.println("Stack Confined took " + time + " ms");

            // leaked
            time = System.currentTimeMillis();
            fillRandomLeaked(numbers);
            time = System.currentTimeMillis() - time;
            System.out.println("Leaked took " + time + " ms");
        }
    }

    public static void fillRandomThreadConfined(int[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = ThreadLocalRandom.current().nextInt();
        }
    }

    public static void fillRandomStackConfined(int[] numbers) {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = tlr.nextInt();
        }
    }

    static ThreadLocalRandom tlr = ThreadLocalRandom.current();

    public static void fillRandomLeaked(int[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = tlr.nextInt();
        }
    }

    public static void fillRandomParallel(int[] numbers) {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();

        class RandomFillerTask extends RecursiveAction {
            private static final int THRESHOLD = 100_000;
            private final int[] numbers;
            private final int offset;
            private final int length;

            RandomFillerTask(int[] numbers, int offset, int length) {
                this.numbers = numbers;
                this.offset = offset;
                this.length = length;
            }

            protected void compute() {
                if (length < THRESHOLD) {
                    for (int i = offset; i < offset + length; i++) {
                        numbers[i] = tlr.nextInt();
                    }
                } else {
                    int offsetLeft = offset;
                    int lengthLeft = length / 2;
                    int offsetRight = offset + lengthLeft;
                    int lengthRight = length - lengthLeft;
                    RandomFillerTask left = new RandomFillerTask(
                            numbers, offsetLeft, lengthLeft
                    );
                    RandomFillerTask right = new RandomFillerTask(
                            numbers, offsetRight, lengthRight
                    );
                    invokeAll(left, right);
                }
            }
        }

        ForkJoinPool fjp = new ForkJoinPool();
        fjp.invoke(new RandomFillerTask(numbers, 0, numbers.length));
        fjp.shutdown();
    }

}
