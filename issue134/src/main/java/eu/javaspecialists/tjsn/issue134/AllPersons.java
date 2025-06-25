package eu.javaspecialists.tjsn.issue134;

import java.util.*;

public class AllPersons {
    private HashMap personsById = new HashMap();
    private HashMap personsByName = new HashMap();

    public Person addPerson(String firstName, String lastName) {
        Person person = new Person(firstName, lastName);
        personsById.put(new Integer(person.getId()), person);
        personsByName.put(firstName + lastName, person);
        return person;
    }

    public Person findPersonsById(int id) {
        return (Person) personsById.get(new Integer(id));
    }

    public Person findPersonsByName(String firstName,
                                    String lastName) {
        return (Person) personsByName.get(firstName + lastName);
    }
}