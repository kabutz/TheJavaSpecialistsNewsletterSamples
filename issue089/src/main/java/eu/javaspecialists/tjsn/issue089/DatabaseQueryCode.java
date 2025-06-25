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
