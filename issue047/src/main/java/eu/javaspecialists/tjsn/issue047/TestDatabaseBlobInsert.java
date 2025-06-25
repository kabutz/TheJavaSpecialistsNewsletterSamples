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

package eu.javaspecialists.tjsn.issue047;

import java.io.*;
import java.sql.*;

public class TestDatabaseBlobInsert {
    private static final String TABLE_DROP =
            "DROP TABLE MovieArchive";
    private static final String TABLE_CREATE =
            "Create Table MovieArchive (moviedata image, title varchar(255))";
    private static final String TABLE_INSERT =
            "INSERT INTO MovieArchive (title, moviedata) VALUES (?,?)";

    private static final int size = 25 * 1024 * 1024;
    private final byte[] data = new byte[size];

    private final Connection con;

    public TestDatabaseBlobInsert(String driver, String url,
                                  String user, String password)
            throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        con = DriverManager.getConnection(url, user, password);
        System.out.println("Driver: " + driver);
        for (int i = 0; i < data.length; i++)
            data[i] = (byte) (Math.random() * 255);
    }

    public void setUp() throws SQLException {
        Statement st = con.createStatement();
        try {
            System.out.println("Dropping old table");
            st.executeUpdate(TABLE_DROP);
        } catch (SQLException ex) {} // table might not exist
        System.out.println("Creating new table");
        st.executeUpdate(TABLE_CREATE);
        st.close();
    }

    public void testInsertWithBinaryStream() throws SQLException {
        long start = -System.currentTimeMillis();
        System.out.println("Inserting via BinaryStream");
        PreparedStatement stmt = con.prepareStatement(TABLE_INSERT);
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        stmt.setString(1, "Babe");
        stmt.setBinaryStream(2, bis, data.length);
        stmt.executeUpdate();
        start += System.currentTimeMillis();
        System.out.println("That took " + start + "ms");
        stmt.close();
    }

    public void testInsertWithSetBytes() throws SQLException {
        long start = -System.currentTimeMillis();
        System.out.println("Inserting via setBytes()");
        PreparedStatement stmt = con.prepareStatement(TABLE_INSERT);
        stmt.setString(1, "On Her Majesty's Secret Service");
        stmt.setBytes(2, data);
        stmt.executeUpdate();
        start += System.currentTimeMillis();
        System.out.println("That took " + start + "ms");
        stmt.close();
    }

    public void testAll() throws SQLException {
        setUp();
        testInsertWithBinaryStream();
        testInsertWithSetBytes();
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 4) usage();
        TestDatabaseBlobInsert test = new TestDatabaseBlobInsert(
                args[0], args[1], args[2], args[3]);
        test.testAll();
    }

    private static void usage() {
        System.out.println(
                "Usage: TestDatabaseBlobInsert driver url username password");
        System.exit(1);
    }
}
