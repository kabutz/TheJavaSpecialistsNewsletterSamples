package eu.javaspecialists.tjsn.issue114;

public class Car {
    private final String registrationNumber;

    public Car(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Car)) return false;
        return registrationNumber == ((Car) o).registrationNumber;
    }

    public int hashCode() {
        return registrationNumber.hashCode();
    }
}
