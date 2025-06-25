package eu.javaspecialists.tjsn.issue221;

public abstract class Transformer<T> {
    public final <R, U, X extends Exception> R transform(
            U u, BiFunctionWithCE<T, U, R, X> transformer) throws X {
        T t = takeTransformer();
        try {
            return transformer.apply(t, u);
        } finally {
            putTransformer(t);
        }
    }

    protected abstract T takeTransformer();

    protected abstract void putTransformer(T t);
}