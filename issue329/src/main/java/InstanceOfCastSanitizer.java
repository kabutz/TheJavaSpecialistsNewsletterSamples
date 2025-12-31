import module java.base;

public final class InstanceOfCastSanitizer
        implements InvocationTargetSantizier {
    public void sanitize(InvocationTargetException e) {
        var cause = e.getCause();
        if (cause instanceof Error)
            throw (Error) cause;
        if (cause instanceof RuntimeException)
            throw (RuntimeException) cause;
        if (cause instanceof IOException)
            throw new UncheckedIOException(
                    (IOException) cause);
        throw new IllegalStateException(cause);
    }
}