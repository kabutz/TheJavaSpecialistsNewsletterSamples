package eu.javaspecialists.tjsn.issue312;

public class OverridingFinalToStringPuzzle {
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
        // TODO: Change this method so that the converted String
        //  becomes "Bear" without any command line flags (thus
        //  no deep reflection on String)
        Bull bull = new Bull();
        // Don't change anything below this line
        String result = convert(bull);
        if (!result.equals("Bear"))
            throw new AssertionError("Should be \"Bear\"");
        System.out.println("All good!");
    }
}
