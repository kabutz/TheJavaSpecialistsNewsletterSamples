package eu.javaspecialists.tjsn.issue312;

public class OverridingFinalToStringSolution {
    private static class Bull {
        public final String toString() {
            return "Bull";
        }
    }

    public static String convert(Bull bull) {
        return "%s".formatted(bull);
    }

    public static void main(String... args) {
        // Don't change anything above this line
        class Bear extends Bull implements java.util.Formattable {
            public void formatTo(java.util.Formatter formatter,
                                 int flags, int width, int precision) {
                formatter.format("Bear");
            }
        }
        Bull bull = new Bear();
        // Don't change anything below this line
        String result = convert(bull);
        if (!result.equals("Bear"))
            throw new AssertionError("Should be \"Bear\"");
        System.out.println("All good!");
    }
}
