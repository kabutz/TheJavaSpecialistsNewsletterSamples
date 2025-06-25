package eu.javaspecialists.tjsn.issue024;

public interface FIFO {
    /**
     * Add an object to the end of the FIFO queue
     */
    boolean add(Object o);

    /**
     * Remove an object from the front of the FIFO queue
     */
    Object remove();

    /**
     * Return the number of elements in the FIFO queue
     */
    int size();
}
