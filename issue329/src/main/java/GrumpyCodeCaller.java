import module java.base;

public class GrumpyCodeCaller {
    public void callMethodsByRandom(
            InvocationTargetSantizier sanitizer)
            throws IllegalAccessException {
        System.out.println("Using sanitizer " +
                           sanitizer.getClass());
        var grumpy = new GrumpyCode();
        var methods = GrumpyCode.class.getDeclaredMethods();
        for (int i = 0; i < 10; i++) {
            var method = methods[
                    ThreadLocalRandom.current()
                            .nextInt(methods.length)];
            try {
                method.invoke(grumpy);
            } catch (InvocationTargetException e) {
                try {
                    sanitizer.sanitize(e);
                } catch (Error | RuntimeException ex) {
                    System.out.println(ex);
                }
            }
        }
        System.out.println();
    }

    void main() throws ReflectiveOperationException {
        for (Class<?> sanitizerClass :
                InvocationTargetSantizier.class
                        .getPermittedSubclasses()) {
            var subclass = sanitizerClass.asSubclass(
                    InvocationTargetSantizier.class);
            var sanitizer = subclass
                    .getConstructor()
                    .newInstance();
            callMethodsByRandom(sanitizer);
        }
    }
}
