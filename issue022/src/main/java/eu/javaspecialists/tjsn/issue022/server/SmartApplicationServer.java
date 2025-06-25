package eu.javaspecialists.tjsn.issue022.server;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * An "application server" that copes with mutual application
 * dependencies
 */
public class SmartApplicationServer extends ApplicationServer {
    /**
     * A classloader extension that is able to delegate to other
     * application�s classloaders
     */
    protected class SmartClassLoader extends URLClassLoader {
        /**
         * mirrors parent constructor
         */
        public SmartClassLoader(URL[] urls, ClassLoader parent) {
            super(urls, parent);
        }

        /**
         * dispatch "normal" loadClass method to another name
         */
        protected Class loadClassNormal(String name, boolean resolve)
                throws ClassNotFoundException {
            return super.loadClass(name, resolve);
        }

        /**
         * override "normal" loadClass method in order to delegate
         */
        protected Class loadClass(String name, boolean resolve)
                throws ClassNotFoundException {
            try {
                // first we try it traditionally
                return loadClassNormal(name, resolve);
            } catch (ClassNotFoundException e) {
                // if this doesnt help, we ask the other application
                // threads for help
                synchronized (SmartApplicationServer.this) {
                    Iterator allThreads = applications.values().iterator();
                    while (allThreads.hasNext()) {
                        SmartClassLoader nextLoader = (SmartClassLoader)
                                ((Thread) allThreads.next()).getContextClassLoader();
                        if (nextLoader != null && !nextLoader.equals(this)) {
                            try {
                                // try the context class loader of next thread
                                return nextLoader.loadClassNormal(name, resolve);
                            } catch (ClassNotFoundException _e) {
                            }
                        }
                    }
                    // they could not help us, hence we throw an exception
                    throw new ClassNotFoundException(
                            "class could not be found amongst applications.");
                } // synchronized(SmartApplicationServer.this)
            }  // catch
        } // method loadClass()
    } // class

    /**
     * override parents factory method to construct dedicated
     * classloaders
     */
    public ClassLoader constructClassLoader(URL[] urls,
                                            ClassLoader parent) {
        return new SmartClassLoader(urls, parent);
    }

    /**
     * example of the improved application server
     */
    public static void main(String[] args) throws Exception {
        BufferedReader stdin = new BufferedReader(
                new InputStreamReader(System.in));
        ApplicationServer appServer = new SmartApplicationServer();
        appServer.deploy(getStockLocation(), "stock.Main",
                new String[]{"screwdriver", "stock.DelegatingWarehouseImpl",
                        "vendorScrewer", "20", "vendorScrewer", "stock.WarehouseImpl",
                        "vendorScrewer", "200"}, null);
        appServer.deploy(getSalesLocation(), "sales.Main",
                new String[]{"screwdriver", "50"}, null);
        appServer.start(getStockLocation());
        stdin.readLine();
        appServer.start(getSalesLocation());
        stdin.readLine();
        appServer.undeploy(getSalesLocation());
        appServer.undeploy(getStockLocation());
        System.exit(0);
    }
}
