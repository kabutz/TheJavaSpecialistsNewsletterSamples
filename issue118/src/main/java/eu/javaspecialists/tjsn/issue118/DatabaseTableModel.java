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

package eu.javaspecialists.tjsn.issue118;

import org.apache.commons.dbutils.*;

import javax.swing.table.*;
import java.sql.*;
import java.util.*;

public class DatabaseTableModel extends DefaultTableModel {
    private final QueryRunner queryRunner = new QueryRunner();

    public DatabaseTableModel(Connection con, Object tableName)
            throws SQLException {
        // might need to delimit table names
        String sql = "SELECT * FROM " + tableName;
        queryRunner.query(con, sql, new ResultSetHandler() {
            public Object handle(ResultSet rs) throws SQLException {
                // extract the column names
                int numColumns = rs.getMetaData().getColumnCount();
                Vector column = new Vector();
                for (int i = 1; i <= numColumns; i++) {
                    column.add(rs.getMetaData().getColumnName(i));
                }
                // extract the data
                Vector data = new Vector();
                while (rs.next()) {
                    Vector row = new Vector();
                    for (int i = 1; i <= numColumns; i++) {
                        row.add(rs.getString(i));
                    }
                    data.add(row);
                }
                setDataVector(data, column);
                return null;
            }
        });
    }
}
