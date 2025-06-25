/*
 * Copyright 2000-2025 Heinz Max Kabutz
 * All rights reserved.
 *
 * From The Java Specialists' Newsletter (https://www.javaspecialists.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.javaspecialists.tjsn.issue022.server;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * A tiny "application server"
 */
public class ApplicationServer extends Loader {
    /**
     * this map stores application urls->active-threads
     */
    protected final Map applications = new HashMap();

    /**
     * the deploy method interns an application
     *
     * @param url    jar where the application is packed
     * @param main   fully-qualified name of Main class
     * @param args   arguments to main method
     * @param parent parent classloader
     * @return the classloader that has been generated for that app
     * @throws ClassNotFoundException if application not found
     */
    public synchronized ClassLoader deploy(final URL url,
                                           String main, final String[] args, ClassLoader parent)
            throws ClassNotFoundException {

        System.out.println("Deploying " + url);

        // is this a redeploy?
        if (applications.containsKey(url)) // yes: tear it down first
            undeploy(url);

        // generate a new classloader
        final ClassLoader loader = constructClassLoader(
                new URL[]{url}, parent);
        // load the mainclass
        final Class mainClass = loader.loadClass(main);

        // construct a new "main" thread
        Thread newThread = new Thread() {
            public void run() {
                try {
                    // run the main method with the given args
                    Class[] params = {String[].class}; // args types
                    mainClass.getMethod("main", params).invoke(
                            mainClass, new Object[]{args});
                    // keep application alive until teared down
                    synchronized (this) {this.wait();}
                } catch (java.lang.reflect.InvocationTargetException e) {
                    e.getTargetException().printStackTrace();
                } catch (Exception e) {
                } finally {
                    // we lock the appServer
                    synchronized (ApplicationServer.this) {
                        try {
                            // in case that any application error occurs
                            // (or the application is to be undeployed)
                            System.out.println("Stopping " + url);
                            // remove entry if still there
                            applications.values().remove(this);
                            // call cleanup method
                            mainClass.getMethod("stop", new Class[0]).invoke(
                                    mainClass, new Object[0]);
                        } catch (Exception _e) {}
                    } // synchronized(appServer.this)
                } // finally
            }
        }; // method run(); class Thread()

        // set the thread context
        newThread.setContextClassLoader(loader);
        // register application x thread
        applications.put(url, newThread);
        // return classloader
        return loader;
    } // method deploy()

    /**
     * starts an application that has already been deployed
     */
    public synchronized void start(URL url) {
        System.out.println("Starting " + url);
        ((Thread) applications.get(url)).start();
    }

    /**
     * Undeploys a running application. Never, I repeat, NEVER, do
     * this using Thread.stop() but use the various options that
     * are proposed by your JDK documentation to gracefully notify
     * a thread of shutdown.
     *
     * @param url url where the application is packed
     * @throws Exception if the app could not be teared down
     */
    public synchronized void undeploy(URL url) {
        // uh uh. bastard. But for Heinz newsletter, its ok ;-)
        ((Thread) applications.get(url)).stop(new Exception("stop"));
    }

    /**
     * class loader factory method
     */
    protected ClassLoader constructClassLoader(URL[] urls,
                                               ClassLoader parent) {
        return new URLClassLoader(urls, parent);
    }

    /**
     * example usage of the appServer
     */
    public static void main(String[] args) throws Exception {
        BufferedReader stdin = new BufferedReader(
                new InputStreamReader(System.in));
        ApplicationServer appServer = new ApplicationServer();
        ClassLoader stockLoader = appServer.deploy(
                getStockLocation(), "stock.Main",
                new String[]{"screwdriver", "stock.WarehouseImpl",
                        "screwdriver", "200"},
                null);
        appServer.start(getStockLocation());
        stdin.readLine();
        appServer.deploy(getSalesLocation(),
                "sales.Main",
                new String[]{"screwdriver", "50"},
                stockLoader);
        appServer.start(getSalesLocation());
        stdin.readLine();
        appServer.deploy(appServer.getSalesLocation(),
                "sales.Main",
                new String[]{"screwdriver", "80"},
                stockLoader);
        appServer.start(appServer.getSalesLocation());
        stdin.readLine();
        appServer.undeploy(appServer.getSalesLocation());
        appServer.undeploy(appServer.getStockLocation());
        System.exit(0);
    }
}
