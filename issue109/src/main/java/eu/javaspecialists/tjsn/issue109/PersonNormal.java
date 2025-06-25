package eu.javaspecialists.tjsn.issue109;

public class PersonNormal {
    private final String name;
    private final String address;
    private final int age;

    public PersonNormal(String name, String address, int age) {
        this.name = name;
        this.address = address;
        this.age = age;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonNormal)) return false;

        final PersonNormal personNormal = (PersonNormal) o;

        if (age != personNormal.age) return false;
        if (address != null ?
                !address.equals(personNormal.address) :
                personNormal.address != null)
            return false;
        if (name != null ?
                !name.equals(personNormal.name) :
                personNormal.name != null)
            return false;
        return true;
    }

    public int hashCode() {
        int result;
        result = (name != null ? name.hashCode() : 0);
        result = 29 * result + (address != null ? address.hashCode() : 0);
        result = 29 * result + age;
        return result;
    }
}
