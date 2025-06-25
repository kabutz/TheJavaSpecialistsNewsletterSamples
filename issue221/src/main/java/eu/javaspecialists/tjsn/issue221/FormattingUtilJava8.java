package eu.javaspecialists.tjsn.issue221;

import java.time.*;
import java.time.format.*;

public class FormattingUtilJava8 implements
        FormatterParser<LocalDate, RuntimeException> {
    private static DateTimeFormatter formatter =
            DateTimeFormatter.ISO_DATE;

    public String format(LocalDate date) {
        return formatter.format(date);
    }

    public LocalDate parse(String date) {
        return LocalDate.parse(date);
    }
}