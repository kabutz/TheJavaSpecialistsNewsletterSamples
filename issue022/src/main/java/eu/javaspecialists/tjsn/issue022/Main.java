package eu.javaspecialists.tjsn.issue022;

import java.rmi.*;
import java.util.*;

/**
 * Example service-publishing application
 */
public class Main {
    /**
     * the services provided by this application
     */
    protected static Collection services = new Vector();

    /**
     * create and export services
     */
    public static void main(String[] args) throws Exception {
        System.setSecurityManager(new RMISecurityManager());
        for (int count = 0; count < args.length; count++) {
            services.add(args[count]);
            // use context classloader to resolve class names
            Naming.rebind("//localhost/" + args[count++],
                    (Remote) Thread.currentThread().getContextClassLoader().
                            loadClass(args[count++]).getConstructor(
                                    new Class[]{String.class, int.class}).newInstance(
                                    new Object[]{args[count++],
                                            new Integer(args[count])}));
        }
    }

    /**
     * tearDown services means unPublish
     */
    public static void stop() {
        Iterator allServices = services.iterator();
        while (allServices.hasNext()) {
            try {
                Naming.unbind("//localhost/" + allServices.next());
            } catch (Exception e) {}
        }
    }
}