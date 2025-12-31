import module java.base;

public final class SwitchPatternMatchingSanitizer
        implements InvocationTargetSantizier {
    public void sanitize(InvocationTargetException e) {
        switch (e.getCause()) {
            case Error error -> throw error;
            case RuntimeException re -> throw re;
            case IOException ioe ->
                    throw new UncheckedIOException(ioe);
            case Throwable t ->
                    throw new IllegalStateException(t);
        }
    }
}