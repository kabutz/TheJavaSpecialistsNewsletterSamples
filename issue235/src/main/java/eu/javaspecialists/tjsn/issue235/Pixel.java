package eu.javaspecialists.tjsn.issue235;

public class Pixel implements Comparable<Pixel> {
    public enum HASH_ALGO {
        EFFECTIVE_JAVA {
            public int calc(Pixel pixel) {
                return pixel.x * 31 + pixel.y; // also in Objects.hash()
            }
        },
        BAD_BIT_SHIFT_XOR {
            public int calc(Pixel pixel) {
                // precedence rules mean that this evaluates to
                // pixel.x << (16 + pixel.y).
                return pixel.x << 16 + pixel.y;
            }
        },
        BIT_SHIFT_XOR {
            public int calc(Pixel pixel) {
                return pixel.x << 16 ^ pixel.y; // perfect hash
            }
        },
        MULTIPLY_LARGE_PRIME {
            public int calc(Pixel pixel) {
                return pixel.x * 65521 + pixel.y; // perfect hash
            }
        },
        MULTIPLY_JUST_ENOUGH {
            public int calc(Pixel pixel) {
                return pixel.x * 768 + pixel.y; // perfect hash
            }
        },
        APPENDING_STRINGS {
            public int calc(Pixel pixel) {
                // no idea if perfect hash, also creates lots of garbage
                return (pixel.x + "-" + pixel.y).hashCode();
            }
        },
        TERRIBLE {
            public int calc(Pixel pixel) {
                // always the same value, just bad bad bad
                return 0;
            }
        };

        public abstract int calc(Pixel pixel);
    }

    private static HASH_ALGO algo = HASH_ALGO.EFFECTIVE_JAVA;

    public static void setAlgo(HASH_ALGO algo) {
        Pixel.algo = algo;
    }

    private final int x;
    private final int y;

    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pixel pixel = (Pixel) o;

        return x == pixel.x && y == pixel.y;
    }

    public int hashCode() {
        return algo.calc(this);
    }

    public int compareTo(Pixel o) {
        int result = compare(x, o.x);
        if (result == 0) {
            result = compare(y, o.y);
        }
        return result;
    }

    private int compare(int x1, int x2) {
        return (x1 < x2) ? -1 : ((x1 == x2) ? 0 : 1);
    }
}
