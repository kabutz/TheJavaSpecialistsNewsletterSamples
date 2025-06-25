package eu.javaspecialists.tjsn.issue109;

import java.util.*;

public class PerfTest {
    public static void main(String... args) {
        PersonNormal[] ppl = {
                new PersonNormal("Heinz Kabutz", "no", 33),
                new PersonNormal(new String("Heinz Kabutz"), "no", 33),
                new PersonNormal("Znieh", "no", 33),
                new PersonNormal(null, "no", 33),
                new PersonNormal("Heinz", null, 33),
                new PersonNormal("Heinz", "no", 0),
                null,
        };
        boolean[][] check = compare("Plain", ppl);

        PersonWithStrategy[] ppl2 = {
                new PersonWithStrategy("Heinz Kabutz", "no", 33,
                        new FieldEqualityStrategy()),
                new PersonWithStrategy(new String("Heinz Kabutz"),
                        "no", 33, new FieldEqualityStrategy()),
                new PersonWithStrategy("Znieh", "no", 33,
                        new FieldEqualityStrategy()),
                new PersonWithStrategy(null, "no", 33,
                        new FieldEqualityStrategy()),
                new PersonWithStrategy("Heinz", null, 33,
                        new FieldEqualityStrategy()),
                new PersonWithStrategy("Heinz", "no", 0,
                        new FieldEqualityStrategy()),
                null,
        };
        boolean[][] fieldCheck = compare("FieldEqualityStrategy",
                ppl2);
        check(check, fieldCheck);

        PersonWithStrategy[] ppl3 = {
                new PersonWithStrategy("Heinz Kabutz", "no", 33,
                        new CachedEqualityStrategy(
                                new FieldEqualityStrategy())),
                new PersonWithStrategy(new String("Heinz Kabutz"), "no", 33,
                        new CachedEqualityStrategy(
                                new FieldEqualityStrategy())),
                new PersonWithStrategy("Znieh", "no", 33,
                        new CachedEqualityStrategy(
                                new FieldEqualityStrategy())),
                new PersonWithStrategy(null, "no", 33,
                        new CachedEqualityStrategy(
                                new FieldEqualityStrategy())),
                new PersonWithStrategy("Heinz", null, 33,
                        new CachedEqualityStrategy(
                                new FieldEqualityStrategy())),
                new PersonWithStrategy("Heinz", "no", 0,
                        new CachedEqualityStrategy(
                                new FieldEqualityStrategy())),
                null,
        };
        boolean[][] cachedFieldCheck = compare(
                "cached FieldEqualityStrategy", ppl3);
        check(check, cachedFieldCheck);

        PersonWithStrategy[] ppl4 = {
                new PersonWithStrategy("Heinz Kabutz", "no", 33,
                        new ValueBasedEqualityStrategy()),
                new PersonWithStrategy(new String("Heinz Kabutz"), "no", 33,
                        new ValueBasedEqualityStrategy()),
                new PersonWithStrategy("Znieh", "no", 33,
                        new ValueBasedEqualityStrategy()),
                new PersonWithStrategy(null, "no", 33,
                        new ValueBasedEqualityStrategy()),
                new PersonWithStrategy("Heinz", null, 33,
                        new ValueBasedEqualityStrategy()),
                new PersonWithStrategy("Heinz", "no", 0,
                        new ValueBasedEqualityStrategy()),
                null,
        };
        boolean[][] varArgsCheck = compare(
                "ValueBasedEqualityStrategy", ppl4);
        check(check, varArgsCheck);

        PersonWithStrategy[] ppl5 = {
                new PersonWithStrategy("Heinz Kabutz", "no", 33,
                        new CachedEqualityStrategy(
                                new ValueBasedEqualityStrategy())),
                new PersonWithStrategy(new String("Heinz Kabutz"), "no", 33,
                        new CachedEqualityStrategy(
                                new ValueBasedEqualityStrategy())),
                new PersonWithStrategy("Znieh", "no", 33,
                        new CachedEqualityStrategy(
                                new ValueBasedEqualityStrategy())),
                new PersonWithStrategy(null, "no", 33,
                        new CachedEqualityStrategy(
                                new ValueBasedEqualityStrategy())),
                new PersonWithStrategy("Heinz", null, 33,
                        new CachedEqualityStrategy(
                                new ValueBasedEqualityStrategy())),
                new PersonWithStrategy("Heinz", "no", 0,
                        new CachedEqualityStrategy(
                                new ValueBasedEqualityStrategy())),
                null,
        };
        boolean[][] cachedVarArgsCheck = compare(
                "cached ValueBasedEqualityStrategy", ppl5);
        check(check, cachedVarArgsCheck);

        PersonWithStrategy[] ppl6 = {
                new PersonWithStrategy("Heinz Kabutz", "no", 33),
                new PersonWithStrategy(new String("Heinz Kabutz"), "no", 33),
                new PersonWithStrategy("Znieh", "no", 33),
                new PersonWithStrategy(null, "no", 33),
                new PersonWithStrategy("Heinz", null, 33),
                new PersonWithStrategy("Heinz", "no", 0),
                null,
        };
        compare("NullEqualityStrategy", ppl6);
    }

    private static boolean[][] compare(String strategy,
                                       Object[] ppl) {
        // first check correctness
        boolean[][] result = new boolean[ppl.length][ppl.length];
        for (int i = 0; i < ppl.length; i++) {
            for (int j = 0; j < ppl.length; j++) {
                if (ppl[i] != null) {
                    result[i][j] = ppl[i].equals(ppl[j]);
                }
            }
        }
        // now check performance
        long time = System.currentTimeMillis();
        for (int k = 0; k < 100 * 1000; k++) {
            for (int i = 0; i < ppl.length; i++) {
                for (int j = 0; j < ppl.length; j++) {
                    if (ppl[i] != null) ppl[i].equals(ppl[j]);
                }
            }
        }
        time = System.currentTimeMillis() - time;
        System.out.println(strategy + " equals() " + time + "ms");

        time = System.currentTimeMillis();
        for (int k = 0; k < 1000 * 1000; k++) {
            for (int i = 0; i < ppl.length; i++) {
                if (ppl[i] != null) ppl[i].hashCode();
            }
        }
        time = System.currentTimeMillis() - time;
        System.out.println(strategy + " hashCode() " + time + "ms");
        return result;
    }

    private static void check(boolean[][] check,
                              boolean[][] fieldCheck) {
        if (!Arrays.deepEquals(check, fieldCheck)) {
            System.out.println(
                    "check = " + Arrays.deepToString(check));
            System.out.println(
                    "other = " + Arrays.deepToString(fieldCheck));
            throw new RuntimeException();
        }
    }
}
