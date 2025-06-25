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
