package eu.javaspecialists.tjsn.issue221;

@FunctionalInterface
public interface SupplierWithCE<T, X extends Exception> {
    T get() throws X;
}