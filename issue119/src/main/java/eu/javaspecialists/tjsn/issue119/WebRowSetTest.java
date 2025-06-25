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

package eu.javaspecialists.tjsn.issue119;

import com.sun.rowset.*;

import javax.sql.rowset.*;

public class WebRowSetTest {
    public static void main(String[] args) throws Exception {
        if (args.length != 5) {
            System.err.println("Usage: java WebRowSetTest " +
                    "driver url user password table");
            System.exit(1);
        }

        int column = 0;
        String driver = args[column++];
        String url = args[column++];
        String user = args[column++];
        String password = args[column++];
        String table = args[column++];

        Class.forName(driver);

        WebRowSet data = new WebRowSetImpl(); // rather use a factory
        data.setCommand("SELECT * FROM " + table);
        data.setUsername(user);
        data.setPassword(password);
        data.setUrl(url);
        data.execute(); // executes command and populates webset

        data.writeXml(System.out);
        data.close();
    }
}
