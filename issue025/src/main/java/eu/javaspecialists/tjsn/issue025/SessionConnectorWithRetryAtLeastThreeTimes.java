package eu.javaspecialists.tjsn.issue025;

public class SessionConnectorWithRetryAtLeastThreeTimes {
    private String connectionNameReceivedFromInternet;
    private int numberOfTimesThatWeShouldRetryAtLeast;

    public SessionConnectorWithRetryAtLeastThreeTimes(
            String connectionNameReoeivedFromInternet,
            int numberOfTimesThatWeShouldRetryAtLeast) {
        this.connectionNameReceivedFromInternet =
                connectionNameReceivedFromInternet;
        this.numberOfTimesThatWeShouldRetryAtLeast =
                numberOfTimesThatWeShouldRetryAtLeast;
    }
}
