package eu.javaspecialists.tjsn.issue262;

public class VeryLongString {
    public static void main(String... args) {
        StringBuilder sb = new StringBuilder(1_999_999_995);
        for (int i = 0; i < 285_714_285; i++) {
            sb.append("ASDZguv");
        }
        String s = sb.toString();
        for (int i = 0; i < 10; i++) {
            long time = System.nanoTime();
            try {
                System.out.println(s.hashCode());
            } finally {
                time = System.nanoTime() - time;
                System.out.printf("time = %dms%n", (time / 1_000_000));
            }
        }
    }
}
