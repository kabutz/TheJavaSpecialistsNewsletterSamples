import module java.base;
import module java.sql;

public final class GrumpyCode {
    public void brokenTable() throws SQLException {
        throw new SQLException("Can't stand JDBC");
    }

    public void talkToWife() {
        throw new IllegalArgumentException(
                "Who left the toilet seat up?");
    }

    public void whereAreMyGlasses() {
        throw new OutOfMemoryError("On my head!");
    }

    public void talkToSelf() throws IOException {
        throw new IOException("Connection closed");
    }
}
