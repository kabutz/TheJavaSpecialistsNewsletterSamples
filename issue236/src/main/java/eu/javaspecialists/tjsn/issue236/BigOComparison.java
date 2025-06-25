package eu.javaspecialists.tjsn.issue236;

public class BigOComparison {
    public static void main(String... args) {
        for (int n = 1; n <= 1_000_000_000; n *= 10) {
            double n_2 = Math.pow(n, 2);
            double n_1_585 = Math.pow(n, 1.585);
            double n_1_465 = Math.pow(n, 1.465);

            double karatsuba_faster_than_quadratic = n_2 / n_1_585;
            double toom_cook_faster_than_karatsuba = n_1_585 / n_1_465;

            System.out.printf("%d\t%.2fx\t%.2fx%n",
                    n, karatsuba_faster_than_quadratic,
                    toom_cook_faster_than_karatsuba);
        }
    }
}
