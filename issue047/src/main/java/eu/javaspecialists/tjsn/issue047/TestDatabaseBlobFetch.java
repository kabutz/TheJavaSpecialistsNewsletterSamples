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

public class TestDatabaseBlobFetch {
    private static final String TABLE_SELECT =
            "SELECT moviedata FROM MovieArchive WHERE title = ?";

    private final Connection con;

    public TestDatabaseBlobFetch(String driver, String url,
                                 String user, String password)
            throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        con = DriverManager.getConnection(url, user, password);
        System.out.println("Driver: " + driver);
    }

    public void testSelectBlocksAtATime() throws SQLException {
        long start = -System.currentTimeMillis();
        System.out.println("SELECT: 64kb blocks at a time");
        PreparedStatement stmt = con.prepareStatement(TABLE_SELECT);
        stmt.setString(1, "Babe");
        ResultSet rs = stmt.executeQuery();
        int count = 0;
        if (rs.next()) {
            try {
                System.out.println("Retrieving Data");
                OutputStream out = new BufferedOutputStream(
                        new FileOutputStream("Data.1"));
                InputStream in = new BufferedInputStream(
                        rs.getBinaryStream(1));
                byte[] buf = new byte[65536];
                int i;
                while ((i = in.read(buf, 0, buf.length)) != -1) {
                    out.write(buf, 0, i);
                    count += i;
                }
                out.close();
            } catch (IOException ex) {ex.printStackTrace();}
        }
        System.out.println("fetched " + count + " bytes");
        start += System.currentTimeMillis();
        System.out.println("That took " + start + "ms");
        stmt.close();
    }

    public void testSelectWithGetBytes() throws SQLException {
        long start = -System.currentTimeMillis();
        System.out.println("SELECT: all at once");
        PreparedStatement stmt = con.prepareStatement(TABLE_SELECT);
        stmt.setString(1, "Babe");
        ResultSet rs = stmt.executeQuery();
        byte[] data = null;
        if (rs.next()) {
            System.out.println("Retrieving Data");
            data = rs.getBytes(1);
            try {
                FileOutputStream out = new FileOutputStream("Data.2");
                out.write(data, 0, data.length);
                out.close();
            } catch (IOException ex) {ex.printStackTrace();}
        }
        System.out.println("fetched " + data.length + " bytes");
        start += System.currentTimeMillis();
        System.out.println("That took " + start + "ms");
        stmt.close();
    }

    public void testAll() throws SQLException {
        testSelectBlocksAtATime();
        testSelectWithGetBytes();
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 4) usage();
        TestDatabaseBlobFetch test = new TestDatabaseBlobFetch(
                args[0], args[1], args[2], args[3]);
        test.testAll();
    }

    private static void usage() {
        System.out.println(
                "usage: TestDatabaseBlobFetch driver url username password");
        System.exit(1);
    }
}
