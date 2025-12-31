import module java.base;

public final class ThrowUsingSwitchExpressionSanitizer {
    public void sanitize(InvocationTargetException e) {
        /*
        // Does not compile:
        //   Unhandled exception: java.lang.Throwable
        throw switch (e.getCause()) {
            case Error error -> error;
            case RuntimeException re -> re;
            case IOException ioe ->
                    new UncheckedIOException(ioe);
            case Throwable throwable ->
                    new IllegalStateException(throwable);
        };
        */
    }
}