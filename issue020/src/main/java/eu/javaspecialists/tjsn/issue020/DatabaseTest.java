package eu.javaspecialists.tjsn.issue020;

import java.io.*;
import java.sql.*;
import java.util.*;

public class DatabaseTest {
    public static void write(
            Object obj, PreparedStatement ps, int parameterIndex)
            throws SQLException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oout = new ObjectOutputStream(baos);
        oout.writeObject(obj);
        oout.close();
        // This will NOT work in JDBC-ODBC bridge under JDK 1.2.2
        // as soon as the size of the byte array is bigger than 2000
        ps.setBytes(parameterIndex, baos.toByteArray());
    }

    public static Object read(ResultSet rs, String column)
            throws SQLException, IOException, ClassNotFoundException {
        // This will NOT work in JDBC-ODBC bridge under JDK 1.2.2
        // as a SQL NULL data value is not handled correctly.
        byte[] buf = rs.getBytes(column);
        if (buf != null) {
            ObjectInputStream objectIn = new ObjectInputStream(
                    new ByteArrayInputStream(buf));
            return objectIn.readObject();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        Connection con = DriverManager.getConnection(
                "jdbc:odbc:MailingList", "sa", "");

        Statement st = con.createStatement();
        st.executeUpdate("INSERT BlobTable (Data) VALUES (NULL)");
        st.close();

        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO BlobTable (Data) VALUES (?)");
        write(new Vector(2) {{
            add("Hello");
            add("World");
        }}, ps, 1);
        ps.execute();
        Vector veryBig = new Vector(10);
        for (int i = 0; i < 10; i++) veryBig.add(new byte[10000]);
        write(veryBig, ps, 1);
        ps.execute();
        write("What Gives?", ps, 1);
        ps.execute();
        write(null, ps, 1);
        ps.execute();
        ps.close();

        st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT Data FROM BlobTable");
        while (rs.next()) {
            System.out.println(read(rs, "Data"));
        }
        rs.close();
        st.close();
    }
}