import module java.base;

public final class TryThrowSanitizer
        implements InvocationTargetSantizier {
    public void sanitize(InvocationTargetException e) {
        try {
            throw e.getCause();
        } catch (RuntimeException | Error unchecked) {
            throw unchecked;
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        } catch (Throwable t) {
            throw new IllegalStateException(t);
        }
    }
}