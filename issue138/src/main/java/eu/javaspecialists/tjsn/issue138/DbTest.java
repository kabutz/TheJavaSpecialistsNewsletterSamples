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

package eu.javaspecialists.tjsn.issue138;

import java.sql.*;

public class DbTest {
    private static final String dburl = "jdbc:derby:tjsnTest";

    public static void main(String... args) throws SQLException {
        Connection con = getNewConnection();
        Statement s = con.createStatement();

        try {
            s.execute("hello world - this should not work");
        } catch (SQLSyntaxErrorException ex) {
            System.out.println("Permanent problem with syntax");
        }

        s.execute("create table testTable(id int, name varchar(10))");
        try {
            s.execute("insert into testTable values (1, 'Heinz Kabutz')");
        } catch (SQLDataException ex) {
            System.out.println("Permanent problem with the data input");
        }

        System.out.println("Is connection valid? " + con.isValid(10));

        shutdownDB();

        System.out.println("Is connection valid? " + con.isValid(10));

        try {
            s.execute("drop table testTable");
        } catch (SQLTransientConnectionException ex) {
            System.out.println("Temporary problem connecting to db");
        }

        // restarting the database
        con = getNewConnection();
        s = con.createStatement();
        try {
            s.execute("drop table testTable");
        } catch (SQLTransientConnectionException ex) {
            System.out.println("Temporary problem connecting to db");
        }

        try {
            s.executeQuery("SELECT id, name FROM testTable");
        } catch (SQLSyntaxErrorException ex) {
            System.out.println("Permanent syntax problem with query");
        }
    }

    // shutting down the database
    private static void shutdownDB() throws SQLException {
        try {
            DriverManager.getConnection(dburl + ";shutdown=true");
        } catch (SQLTransientConnectionException ex) {
            // this should not happen - but it does ...
            System.out.println("Temporary problem connecting to db");
        }
    }

    private static Connection getNewConnection() throws SQLException {
        return DriverManager.getConnection(dburl + ";create=true");
    }
}
