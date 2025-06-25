package eu.javaspecialists.tjsn.issue103;

import java.io.*;

/**
 * @author Heinz Kabutz
 * @since 2005/02/07
 */
public class PersonParserTest {
    private static final String FILE = "persons.txt";

    public static void main(String... args) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(FILE));
        out.println("Heinz,Kabutz,33");
        out.println("John,Green,33");
        out.println("Anton,de Swardt,33");
        out.println("Zachary,Thalla,32");
        out.close();

        InputParser<Person> personFile = new InputParser<Person>(
                new FileInputStream(FILE), new PersonParser());
        for (Person person : personFile) {
            System.out.println(person);
        }
    }
}
