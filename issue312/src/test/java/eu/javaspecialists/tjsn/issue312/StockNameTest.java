package eu.javaspecialists.tjsn.issue312;

import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class StockNameTest {
    @Test
    public void testStockName() {
        var sn = new StockName("HUGE",
                "Huge Fruit, Inc.", "Fruit Titanesque, Inc.");

        test("HUGE - Huge Fruit, Inc.", "%s", sn.toString());
        test("Huge Fruit, Inc.", "%s", sn);
        test("HUGE FRUIT, INC.", "%S", sn);
        test("HUGE", "%#s", sn);
        test("HUGE      ", "%-10.8s", sn);
        test("      HUGE", "%10.8s", sn);
        test("          ", "%10.0s", sn);
        test("HU*", "%.3s", sn);
        test("Huge Fruit,*", "%.12s", sn);
        test("   Fruit Titanesque, Inc.", Locale.FRANCE, "%25s", sn);
        test("HUGE", Locale.FRANCE, "%#s", sn);
    }

    private static void test(String expected, String format,
                             Object arg) {
        test(expected, Locale.US, format, arg);
    }

    private static void test(String expected, Locale locale,
                             String format, Object arg) {
        var formatter = new Formatter();
        var formatted = formatter.format(locale, format, arg);
        assertEquals(expected, formatted.toString());
        System.out.println("\"" + formatted + "\"");
    }
}
