package eu.javaspecialists.tjsn.issue136;

import com.jamonapi.proxy.*;

import java.sql.*;
import java.util.*;

public class SneakerDriverTest {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://myserver:3306/db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    // Whatever query you use here, it should take a long time
    private static final String LONG_QUERY = "SELECT name FROM " +
            "very_large_table WHERE ssn='23432343'";

    public static void main(String... args)
            throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        Connection con = DriverManager.getConnection(
                URL, USERNAME, PASSWORD);
        Statement st = con.createStatement();
        long time = System.currentTimeMillis();
        st.executeQuery(LONG_QUERY);
        time = System.currentTimeMillis() - time;
        System.out.println("time = " + time + "ms");
        System.out.println(
                Arrays.toString(MonProxyFactory.getSQLDetailHeader()));
        Object[][] sqlDetail = MonProxyFactory.getSQLDetail();
        for (int i = 0; i < sqlDetail.length; i++) {
            System.out.println(Arrays.toString(sqlDetail[i]));
        }
        st.close();
        con.close();
    }
}
