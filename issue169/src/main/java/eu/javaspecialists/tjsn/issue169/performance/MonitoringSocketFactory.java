package eu.javaspecialists.tjsn.issue169.performance;

import java.net.*;

public class MonitoringSocketFactory
        implements SocketImplFactory {
    public SocketImpl createSocketImpl() {
        try {
            return new MonitoringSocketImpl();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
