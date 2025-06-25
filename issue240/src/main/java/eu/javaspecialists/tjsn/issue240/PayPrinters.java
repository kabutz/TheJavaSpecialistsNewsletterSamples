package eu.javaspecialists.tjsn.issue240;

import java.text.*;
import java.util.*;

public class PayPrinters {
    public static void main(String... args) throws Exception {
        Locale en_ZA = new Locale("en", "ZA");
        NumberFormat format = NumberFormat.getInstance(en_ZA);
        System.out.printf(en_ZA, "%,.2f%n", 123456.78);
        parse(format, "123 456,78"); // normal space
        parse(format, "123 456.78"); // normal space
        parse(format, "123\u00a0456,78"); // no-break space
        parse(format, "123\u00a0456.78"); // no-break space
        parse(format, "123456,78");
        parse(format, "123456.78");
        parse(format, "123.456,78");
        parse(format, "123,456.78");
    }

    private static void parse(NumberFormat format, String number)
            throws ParseException {
        System.out.println("parse(\"" + number + "\") = " +
                format.parse(number));
    }
}
