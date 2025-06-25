package eu.javaspecialists.tjsn.issue103;

/**
 * @author Heinz Kabutz
 * @since 2005/02/07
 */
public class PersonParser implements InputParser.Parser<Person> {
    public Person convert(String s) {
        String[] values = InputParser.CSV_PARSER.convert(s);
        String firstName = values[0];
        String surname = values[1];
        int age = Integer.parseInt(values[2]);
        return new Person(firstName, surname, age);
    }
}
