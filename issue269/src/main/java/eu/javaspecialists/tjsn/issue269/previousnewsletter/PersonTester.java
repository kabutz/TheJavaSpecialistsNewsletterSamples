package eu.javaspecialists.tjsn.issue269.previousnewsletter;


import eu.javaspecialists.tjsn.issue269.*;

public class PersonTester {
    public static void main(String... args) {
        Person[] people = {
                new Programmer("Aaron", (short) 130),
                new Person("Adolf"),
                new Programmer("Brian", (short) 180),
                new Person("Brian"),
                new Programmer("Cedric", (short) 120),
                new Programmer("Cedric", (short) 120),
                new Programmer("Zoran", (short) 200),
        };

        CompareTester.test(people);
    }
}
