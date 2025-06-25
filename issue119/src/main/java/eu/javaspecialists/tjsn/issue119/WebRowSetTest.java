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
