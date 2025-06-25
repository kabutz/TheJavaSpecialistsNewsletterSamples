package eu.javaspecialists.tjsn.issue089;

import java.sql.*;
import java.util.*;

/**
 * You'll have to compile with JDK 5 and use the switch
 * javac -source 1.5
 */
public class DatabaseQueryCode {
    private final Connection con;

    public DatabaseQueryCode(Connection con) {
        this.con = con;
    }

    /**
     * Take a list of Strings and execute all the queries in one
     * transaction.
     */
    public void executeQueries(List<String> queries) throws SQLException {
        con.setAutoCommit(false);
        Statement st = con.createStatement();
        for (String s : queries) { // I love this construct :-)
            st.execute(s);
        }
        con.commit();
        st.close();
        con.setAutoCommit(true);
    }
}
