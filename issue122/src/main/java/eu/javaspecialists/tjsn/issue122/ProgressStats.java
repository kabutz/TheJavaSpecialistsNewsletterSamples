package eu.javaspecialists.tjsn.issue122;

public class ProgressStats extends Stats {
    private final long contentLength;

    public ProgressStats(long contentLength) {
        this.contentLength = contentLength;
    }

    public String calculatePercentageComplete(int totalBytes) {
        return Long.toString((totalBytes * 100L / contentLength));
    }
}

