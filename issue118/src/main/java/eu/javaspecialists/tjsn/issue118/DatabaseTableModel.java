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
