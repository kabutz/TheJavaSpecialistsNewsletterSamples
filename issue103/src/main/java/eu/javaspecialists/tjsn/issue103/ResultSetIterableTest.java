package eu.javaspecialists.tjsn.issue103;

import java.sql.*;
import java.util.*;

/**
 * Here I am connecting to a database table and selecting all
 * rows, then using the new for/in construct to iterate through
 * the ResultSet.
 *
 * @author Heinz Kabutz
 * @since 2005/02/07
 */
public class ResultSetIterableTest {
    public static void main(String... args) throws Exception {
        // you have to set up your JDBC connection yourself
        Class.forName("...");
        Connection con = DriverManager.getConnection("...");
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM customers");
        for (String[] row : new ResultSetIterable(rs)) {
            // Arrays.toString() is a new Tiger function
            System.out.println(Arrays.toString(row));
        }
    }
}
