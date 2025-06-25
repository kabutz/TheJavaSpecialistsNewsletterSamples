package eu.javaspecialists.tjsn.issue276;

public record PersonRecord(String firstName, String lastName)
        implements Person, java.io.Serializable {
    public PersonRecord(String firstName) {
        this(firstName, null);
    }

    public PersonRecord {
        if ("Heinz".equals(firstName))
            throw new IllegalArgumentException(
                    "\"%s\" is trademarked".formatted(firstName));
    }
}
