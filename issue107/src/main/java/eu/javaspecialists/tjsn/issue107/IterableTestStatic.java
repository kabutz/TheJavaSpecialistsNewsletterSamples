package eu.javaspecialists.tjsn.issue107;

import java.sql.*;
import java.util.*;

public class IterableTestStatic {
    public static void main(String... args) {
        Vector<String> sv = new Vector<String>();
        sv.addElement("Maximilian");
        sv.addElement("Francis");
        sv.addElement("Kabutz");

        // Use a static "factory method" to reduces generics clutter
        for (String s : IterableEnumeration.make(sv.elements())) {
            System.out.println(s);
        }

        // This also looks slightly more readable
        // new sun.jdbc.odbc.JdbcOdbcDriver();
        for (Driver driver : IterableEnumeration.make(
                DriverManager.getDrivers())) {
            System.out.println("driver = " + driver.getClass());
        }
    }
}
