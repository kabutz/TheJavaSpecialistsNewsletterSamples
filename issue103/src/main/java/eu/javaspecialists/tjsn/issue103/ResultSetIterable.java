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
