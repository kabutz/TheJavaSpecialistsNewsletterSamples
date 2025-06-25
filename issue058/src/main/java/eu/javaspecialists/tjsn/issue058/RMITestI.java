package eu.javaspecialists.tjsn.issue058;

import java.rmi.*;
import java.util.*;

public interface RMITestI extends Remote {
    String NAME = "rmitest";

    Map getValues(Map old) throws RemoteException;
}
