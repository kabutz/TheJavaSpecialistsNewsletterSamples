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
