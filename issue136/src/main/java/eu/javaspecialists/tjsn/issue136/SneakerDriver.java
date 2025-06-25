package eu.javaspecialists.tjsn.issue136;

import com.jamonapi.proxy.*;

import java.sql.*;
import java.util.*;
import java.util.logging.*;

public class SneakerDriver implements Driver {
    static {
        try {
            DriverManager.registerDriver(new SneakerDriver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean acceptsURL(String url) throws SQLException {
        return true;
    }

    public Connection connect(String url, Properties info)
            throws SQLException {
        SQLException reason = null;
        Enumeration en = DriverManager.getDrivers();
        while (en.hasMoreElements()) {
            Driver driver = (Driver) en.nextElement();
            if (!(driver instanceof SneakerDriver)) {
                try {
                    Connection result = driver.connect(url, info);
                    if (result != null) {
                        // Success!
                        return MonProxyFactory.monitor(result);
                    }
                } catch (SQLException ex) {
                    reason = ex;
                }
            }
        }
        // if we got here nobody could connect.
        if (reason != null) {
            throw reason;
        }

        throw new SQLException(
                "No suitable driver found for " + url, "08001");
    }

    public DriverPropertyInfo[] getPropertyInfo(String url,
                                                Properties info)
            throws SQLException {
        return new DriverPropertyInfo[0];
    }

    public int getMajorVersion() {
        return 0;
    }

    public int getMinorVersion() {
        return 1;
    }

    public boolean jdbcCompliant() {
        return true;
    }

    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException("not implemented");
    }
}
