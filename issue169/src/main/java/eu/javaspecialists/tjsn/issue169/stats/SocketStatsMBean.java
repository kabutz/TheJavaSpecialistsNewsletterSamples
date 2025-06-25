package eu.javaspecialists.tjsn.issue169.stats;

public interface SocketStatsMBean {
    void printStats();

    void reset();

    long getBytesRead();

    long getBytesWritten();

    String getIndividualBytesWritten();

    String getIndividualBytesRead();
}