package eu.javaspecialists.tjsn.issue107;

import java.sql.*;
import java.util.*;

public class IterableTest {
    public static void main(String... args) {
        Vector<String> sv = new Vector<String>();
        sv.addElement("Maximilian");
        sv.addElement("Francis");
        sv.addElement("Kabutz");

        // using the generics makes it look a bit clumsy
        IterableEnumeration<String> ie =
                new IterableEnumeration<String>(sv.elements());
        for (String s : ie) {
            System.out.println(s);
        }

        // Without generics, we cannot automatically cast to String
        IterableEnumeration ie2 =
                new IterableEnumeration(sv.elements());
        for (Object s : ie2) { // here we now have to use Object type
            System.out.println(s);
        }

        // Again, generics makes the code look clumsy
        // here you should load your own driver, if applicable
        // new sun.jdbc.odbc.JdbcOdbcDriver();
        IterableEnumeration<Driver> drivers =
                new IterableEnumeration<Driver>(
                        DriverManager.getDrivers());
        for (Driver driver : drivers) {
            System.out.println("driver = " + driver.getClass());
        }

        // or we could build up the list using Collections.list()
        // and iterate through that - this is ineffient.
        for (Driver driver : Collections.list(
                DriverManager.getDrivers())) {
            System.out.println("driver = " + driver.getClass());
        }
    }
}
