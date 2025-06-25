package eu.javaspecialists.tjsn.issue022.server;

import java.io.*;
import java.net.*;

public class Loader {
    /**
     * Demonstration of some classloading issues
     */
    public static void main(String[] args) throws Exception {
        // construct a tiny classloader hierarchy
        ClassLoader stockLoader = new URLClassLoader(
                new URL[]{getStockLocation()});
        ClassLoader salesLoader = new URLClassLoader(
                new URL[]{getSalesLocation()}, stockLoader);
        // load order class
        Class orderC = salesLoader.loadClass("sales.Order");
        System.out.println(orderC + " loaded from " + salesLoader +
                "; defined by " + orderC.getClassLoader());
        // load warehouse class
        Class wHouseC = salesLoader.loadClass("stock.Warehouse");
        System.out.println(wHouseC + " loaded from " + salesLoader +
                "; defined by " + wHouseC.getClassLoader());
        // analyse class links
        System.out.println("loading and linking same " +
                wHouseC.equals(orderC.getDeclaredField("wHouse").getType()));
        System.exit(0);
    }

    /**
     * where the stock classes can be found
     */
    protected static URL getStockLocation() throws IOException {
        return new File("classes/stock/").toURL();
    }

    /**
     * where the sales classes can be found
     */
    protected static URL getSalesLocation() throws IOException {
        return new File("classes/sales/").toURL();
    }
}
