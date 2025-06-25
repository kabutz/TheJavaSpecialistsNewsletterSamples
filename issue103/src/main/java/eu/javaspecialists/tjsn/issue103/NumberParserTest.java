package eu.javaspecialists.tjsn.issue103;

import java.io.*;

/**
 * @author Heinz Kabutz
 * @since 2005/02/07
 */
public class NumberParserTest {
    public static void main(String... args) {
        // Create a byte array that contains 10 random numbers less
        // than 10000.
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bout);
        for (int i = 0; i < 10; i++) {
            out.println(Math.random() * 10000);
        }
        out.close();
        byte[] data = bout.toByteArray();

        // Read the Strings in and convert them to doubles.
        InputParser<Double> doubleFile = new InputParser<Double>(
                new ByteArrayInputStream(data), InputParser.DOUBLE_PARSER);
        double total = 0;
        for (double d : doubleFile) { // this uses autoboxing
            total += d;
        }
        System.out.println(total);

        InputParser<Number> numberFile = new InputParser<Number>(
                new ByteArrayInputStream(data), InputParser.INT_PARSER);
        for (Number i : numberFile) {
            System.out.println(i);
        }
    }
}
