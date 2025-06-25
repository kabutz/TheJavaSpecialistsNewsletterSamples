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

package eu.javaspecialists.tjsn.issue103;

import java.io.*;
import java.util.*;

/**
 * I thought for a while for a good name for this class.  This
 * was the best I could come up with, but I am not 100% happy
 * with it either.
 *
 * @author Heinz Kabutz
 * @since 2005/02/07
 */
public class InputParser<T> implements Iterable<T> {
    private final BufferedReader reader;
    private final Parser<? extends T> parser;

    public InputParser(Reader in, Parser<? extends T> parser) {
        this.parser = parser;
        reader = new BufferedReader(in);
    }

    public InputParser(InputStream in, Parser<? extends T> parser) {
        this(new InputStreamReader(in), parser);
    }

    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private String nextLine;
            private boolean lineReadFromFile = false;

            public boolean hasNext() {
                if (!lineReadFromFile) {
                    try {
                        nextLine = reader.readLine();
                    } catch (IOException e) {
                        return false;
                    }
                    lineReadFromFile = true;
                }
                return nextLine != null;
            }

            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                lineReadFromFile = false;
                return parser.convert(nextLine);
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    /**
     * You use this interface to convert a String to your type of
     * object T.  I prefer defining interfaces closely to where
     * they will be used, hence the inner interface Parser.
     */
    public interface Parser<T> {
        T convert(String s);
    }

    /**
     * The default implementation simply returns the strings.
     */
    public static final Parser<String> STRING_PARSER =
            new Parser<String>() {
                public String convert(String s) {
                    return s;
                }
            };

    public static final Parser<Double> DOUBLE_PARSER =
            new Parser<Double>() {
                public Double convert(String s) {
                    return Double.parseDouble(s);
                }
            };

    public static final Parser<Integer> INT_PARSER =
            new Parser<Integer>() {
                public Integer convert(String s) {
                    return Integer.parseInt(s.replaceAll("\\..*", ""));
                }
            };

    /**
     * A simple CSVParser that should probably be expanded to
     * handle delimited separators, e.g. \,
     */
    public static final Parser<String[]> CSV_PARSER =
            new Parser<String[]>() {
                public String[] convert(String s) {
                    List<String> result = new ArrayList<String>();
                    StringTokenizer st = new StringTokenizer(s, ",");
                    while (st.hasMoreTokens()) {
                        result.add(st.nextToken());
                    }
                    return result.toArray(new String[0]);
                }
            };
}
