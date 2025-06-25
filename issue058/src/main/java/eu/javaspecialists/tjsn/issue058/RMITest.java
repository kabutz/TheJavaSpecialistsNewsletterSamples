package eu.javaspecialists.tjsn.issue058;

import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class RMITest extends UnicastRemoteObject
        implements RMITestI {
    private final Map values = new HashMap();

    public RMITest() throws RemoteException {
    }

    public Map getValues(Map old) {
        synchronized (values) {
            values.putAll(old);
            return values;
        }
    }

    public static void main(String[] args) throws IOException {
        RMISocketFactory.setSocketFactory(
                new MonitoringMasterSocketFactory());
        System.setSecurityManager(new RMISecurityManager());
        Naming.rebind(RMITestI.NAME, new RMITest());
    }
}
