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

package eu.javaspecialists.tjsn.issue116;

import org.apache.commons.dbutils.*;

import java.sql.*;
import java.util.*;

public class Database2 {
    private QueryRunner queryRunner = new QueryRunner();

    public int insertSubscriber(Connection con, String name, String email)
            throws SQLException {
        String sql = "INSERT INTO subscribers (name, email) VALUE (?, ?)";
        Object[] params = {name, email};
        return queryRunner.update(con, sql, params);
    }

    public Collection<Subscriber> selectSubscribers(Connection con)
            throws SQLException {
        String sql = "SELECT name, email FROM subscribers";
        Object[] params = null;
        ResultSetHandler handler = new ResultSetHandler() {
            public Object handle(ResultSet rs) throws SQLException {
                Collection<Subscriber> result = new ArrayList<Subscriber>();
                while (rs.next()) {
                    int column = 1;
                    String name = rs.getString(column++);
                    String email = rs.getString(column++);
                    result.add(new Subscriber(name, email));
                }
                return result;
            }
        };
        return (Collection<Subscriber>) queryRunner.query(
                sql, params, handler);
    }

}
