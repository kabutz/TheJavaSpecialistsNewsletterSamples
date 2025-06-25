package eu.javaspecialists.tjsn.issue116;

public class Subscriber {
    private String name;
    private String email;

    public Subscriber(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
