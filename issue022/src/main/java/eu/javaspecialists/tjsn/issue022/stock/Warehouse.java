package eu.javaspecialists.tjsn.issue022.stock;

/**
 * Sample remote interface that is linked by sales.Order.
 */
public interface Warehouse extends java.rmi.Remote {
    /**
     * Reserve items in this warehouse
     */
    void reserve(int quantity) throws java.rmi.RemoteException;
}