package eu.javaspecialists.tjsn.issue252;

import java.time.*;
import java.util.stream.*;

public class HeinzBirthdayDays {
    public static void main(String... args) {
        StreamSupport.stream(
                        new YearSpliterator(
                                LocalDate.of(2017, Month.DECEMBER, 4)), false)
                .takeWhile(day -> day.getYear() >= 1971) // Java 9
                .map(day -> day + " -> " + day.getDayOfWeek())
                .forEach(System.out::println);
    }
}
