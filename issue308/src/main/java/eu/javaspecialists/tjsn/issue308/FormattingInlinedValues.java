package eu.javaspecialists.tjsn.issue308;

public class FormattingInlinedValues {
    public static final String FIRST_NAME = "Heinz";
    private static final String MIDDLE_NAME = "Max";
    protected static final String LAST_NAME = "Kabutz";

    public static final int AGE = 51;
    public static final double HEIGHT = 1.8824213;
    static final boolean THIN = false;

    /**
     * {@return the details of the author} This includes the
     * {@value "FIRST_NAME=%s" #FIRST_NAME}, the {@value
     * "LAST_NAME=%s" #LAST_NAME}, the {@value "AGE=0x%04x"
     * #AGE} in hexadecimal, and the {@value "HEIGHT=%.2fm"
     * #HEIGHT}. We will leave out the property of {@value
     * "THIN=%B" #THIN} and the {@value "MIDDLE_NAME=%s"
     * #MIDDLE_NAME} as not relevant to writing skills.
     */
    public String toString() {
        return "%s %s of age 0x%04x is %.2fm tall".formatted(FIRST_NAME,
                LAST_NAME, AGE, HEIGHT);
    }

    public static void main(String... args) {
        System.out.println(new FormattingInlinedValues());
    }
}
