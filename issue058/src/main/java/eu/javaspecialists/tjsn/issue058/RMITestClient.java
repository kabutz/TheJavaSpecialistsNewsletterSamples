package eu.javaspecialists.tjsn.issue058;

import java.io.*;
import java.rmi.*;
import java.util.*;

public class RMITestClient {
    public static void main(String args[]) throws Exception {
        System.setSecurityManager(new RMISecurityManager());
        RMITestI test = (RMITestI) Naming.lookup(RMITestI.NAME);
        Map values = new HashMap();
        values.put(new Serializable() {
        }, "Today");
        for (int i = 0; i < 13; i++) {
            System.out.print('.');
            System.out.flush();
            values.putAll(test.getValues(values));
        }
    }
}
