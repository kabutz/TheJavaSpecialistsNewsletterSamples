package eu.javaspecialists.tjsn.issue312;

import java.util.*;

import static java.util.FormattableFlags.*;

public record StockName(String symbol, String companyName,
                        String frenchCompanyName)
        implements Formattable {
    @Override
    public String toString() {
        return String.format("%s - %s", symbol, companyName);
    }

    @Override
    public void formatTo(Formatter formatter, int flags,
                         int width, int precision) {
        var alternate = (flags & ALTERNATE) == ALTERNATE;
        var leftJustify = (flags & LEFT_JUSTIFY) == LEFT_JUSTIFY;
        var uppercase = (flags & UPPERCASE) == UPPERCASE;

        var name = chooseName(alternate, precision, formatter.locale());
        name = applyPrecision(name, precision);
        name = applyWidthAndJustification(name, leftJustify, width);
        name = applyUppercase(name, uppercase);
        formatter.format(name);
    }

    private String chooseName(boolean alternate, int precision,
                              Locale locale) {
        if (alternate) return symbol;
        if (precision != -1 && precision < 10) return symbol;
        if (locale.equals(Locale.FRANCE)) return frenchCompanyName;
        return companyName;
    }

    private static String applyPrecision(String name, int precision) {
        if (precision == -1 || name.length() < precision) {
            return name;
        } else if (precision > 0) {
            return name.substring(0, precision - 1) + '*';
        } else {
            return "";
        }
    }

    private static String applyWidthAndJustification(
            String name, boolean leftJustify, int width) {
        if (width <= name.length()) return name;
        var spaces = " ".repeat(width - name.length());
        return leftJustify ? name + spaces : spaces + name;
    }

    private static String applyUppercase(String name,
                                         boolean uppercase) {
        return uppercase ? name.toUpperCase() : name;
    }
}
