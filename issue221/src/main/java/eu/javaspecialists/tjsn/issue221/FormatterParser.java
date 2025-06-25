package eu.javaspecialists.tjsn.issue221;

public interface FormatterParser<T, X extends Throwable> {
    public String format(T t);

    public T parse(String s) throws X;
}