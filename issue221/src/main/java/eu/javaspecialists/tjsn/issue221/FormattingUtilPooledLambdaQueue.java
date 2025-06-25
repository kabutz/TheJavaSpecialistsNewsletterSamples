package eu.javaspecialists.tjsn.issue221;

import java.text.*;
import java.util.*;

public class FormattingUtilPooledLambdaQueue
        implements FormatterParser<Date, ParseException> {
    private final TransformerObjectPool<DateFormat> pool =
            new TransformerObjectPool<>(
                    () -> new SimpleDateFormat("yyyy-MM-dd"));

    public String format(Date date) {
        return pool.transform(date, DateFormat::format);
    }

    public Date parse(String date)
            throws ParseException {
        return pool.transform(date, DateFormat::parse);
    }
}