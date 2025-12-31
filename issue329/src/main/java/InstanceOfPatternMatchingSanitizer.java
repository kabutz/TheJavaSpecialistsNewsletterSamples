import module java.base;

public final class InstanceOfPatternMatchingSanitizer
        implements InvocationTargetSantizier {
    public void sanitize(InvocationTargetException e) {
        var cause = e.getCause();
        if (cause instanceof Error error)
            throw error;
        if (cause instanceof RuntimeException re)
            throw re;
        if (cause instanceof IOException ioe)
            throw new UncheckedIOException(ioe);
        throw new IllegalStateException(cause);
    }
}