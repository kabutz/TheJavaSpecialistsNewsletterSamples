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

import java.sql.*;
import java.util.*;

/**
 * This class allows you to iterate over a ResultSet using the
 * standard for/in construct.  It always returns String[] for
 * each row, irrespective of the real types of the objects.
 *
 * @author Heinz Kabutz
 * @since 2005/02/07
 */
public class ResultSetIterable implements Iterable<String[]> {
    private final ResultSet rs;
    private final int columns;

    public ResultSetIterable(ResultSet rs) throws SQLException {
        this.rs = rs;
        columns = rs.getMetaData().getColumnCount();
    }

    public Iterator<String[]> iterator() {
        return new Iterator<String[]>() {
            private boolean nextCalled = true;
            private boolean moreObjects;

            public boolean hasNext() {
                if (nextCalled) {
                    try {
                        moreObjects = rs.next();
                        nextCalled = false;
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                return moreObjects;
            }

            public String[] next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                try {
                    String[] values = new String[columns];
                    for (int i = 0; i < values.length; i++) {
                        values[i] = rs.getString(i + 1);
                    }
                    nextCalled = true;
                    return values;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
