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
