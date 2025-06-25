/*
 * Copyright 2000-2025 Heinz Max Kabutz
 * All rights reserved.
 *
 * From The Java Specialists' Newsletter (https://www.javaspecialists.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
