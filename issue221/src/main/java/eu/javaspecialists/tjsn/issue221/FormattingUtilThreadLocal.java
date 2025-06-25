package eu.javaspecialists.tjsn.issue221;

import java.text.*;
import java.util.*;

public class FormattingUtilThreadLocal implements
        FormatterParser<Date, ParseException> {
    public String format(Date date) {
        return getFormatter().format(date);
    }

    public Date parse(String date) throws ParseException {
        return getFormatter().parse(date);
    }

    private static final ThreadLocal<DateFormat> formatter =
            new ThreadLocal<DateFormat>() {
                protected DateFormat initialValue() {
                    return new SimpleDateFormat("yyyy-MM-dd");
                }
            };

    private static DateFormat getFormatter() {
        return formatter.get();
    }
}