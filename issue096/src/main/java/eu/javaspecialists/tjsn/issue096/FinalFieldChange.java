package eu.javaspecialists.tjsn.issue096;

import java.lang.reflect.*;

public class FinalFieldChange {
    private static void change(Person p, String name, Object value)
            throws NoSuchFieldException, IllegalAccessException {
        Field firstNameField = Person.class.getDeclaredField(name);
        firstNameField.setAccessible(true);
        firstNameField.set(p, value);
    }

    public static void main(String... args) throws Exception {
        Person heinz = new Person("Heinz Kabutz", 32);
        change(heinz, "name", "Ng Keng Yap");
        change(heinz, "age", new Integer(27));
        change(heinz, "iq", new Integer(150));
        change(heinz, "country", "Malaysia");
        System.out.println(heinz);
    }
}
