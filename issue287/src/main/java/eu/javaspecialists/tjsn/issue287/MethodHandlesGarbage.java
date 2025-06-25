package eu.javaspecialists.tjsn.issue287;

import java.lang.invoke.*;
import java.lang.reflect.*;

/**
 * In this experiment, we look at the effects of the annotation
 * {@link java.lang.invoke.MethodHandle.PolymorphicSignature}.
 * We will invoke {@link java.lang.Short#compare(short, short)},
 * which takes primitive arguments and returns a primitive. Will
 * these have to be boxed? What about the parameter array? Will
 * that need to be created? With Short#compare we can have
 * parameters and return values that fall outside of Short's
 * boxing cache. The compare() method returns "x - y", whereas
 * Integer and Long both return 0, 1, or -1, to avoid overflow.
 * <p>
 * Reflection wraps exceptions as InvocationTargetException,
 * whereas MethodHandle.invokeExact() throws the original
 * exception as is. Thus, we need to throw Throwable.
 * <p>
 * To see the effects of the PolymorphicSignature, please use the
 * VM parameters -XX:-DoEscapeAnalysis -verbose:gc.
 *
 * @author Dr Heinz M. Kabutz
 */
public class MethodHandlesGarbage {
    public static void main(String... args) throws Throwable {
        reflectiveMethodWithFullCaching();
        reflectiveMethodWithCachedParameterArray();
        reflectiveMethodWithCachedIntegerResult();

        methodHandle1();
        methodHandle2();
        methodHandle3();
    }

    /**
     * The parameter array is cached in our method and the result
     * is 90, thus within the range of the Short boxed cache. We
     * would not expect any Short objects to be created.
     */
    private static void reflectiveMethodWithFullCaching()
            throws Throwable {
        doExperiment(() -> {
            Short[] args = {50, -40};
            for (int i = 0; i < 100_000_000; i++) {
                int result = (int) compareMethod.invoke(null, args);
            }
        });
    }

    /**
     * The parameter array is cached, but the result is 255, thus
     * not in the Short boxed cache. We would expect Short objects
     * to be created in the absence of escape analysis.
     */
    private static void reflectiveMethodWithCachedParameterArray()
            throws Throwable {
        doExperiment(() -> {
            Short[] args = {127, -128};
            for (int i = 0; i < 100_000_000; i++) {
                int result = (int) compareMethod.invoke(null, args);
            }
        });
    }

    /**
     * The parameter array is not cached, thus it needs to be
     * created every time. All the boxed Short instances come from
     * the Short cache. Note that escape analysis is generally
     * good at eliminating this parameter array, but we have seen
     * instances where it was not able to, most notably when using
     * dynamic proxies and with deep call stacks.
     */
    private static void reflectiveMethodWithCachedIntegerResult()
            throws Throwable {
        doExperiment(() -> {
            for (int i = 0; i < 100_000_000; i++) {
                short x = 50, y = -40;
                int result = (int) compareMethod.invoke(null, x, y);
            }
        });
    }

    /**
     * The parameters fall within the range of the Short boxed
     * cache, but the result does not. Despite that, the result
     * is not boxed / unboxed, as we can see in the GC logs.
     * Note that even though the signature of invokeExact() has
     * varargs (...), no array is created either.
     */
    private static void methodHandle1()
            throws Throwable {
        doExperiment(() -> {
            for (int i = 0; i < 100_000_000; i++) {
                short x = 127, y = -128;
                int result = (int) compareMH.invokeExact(x, y);
            }
        });
    }

    /**
     * The parameters call outside the range of the Short boxed
     * cache, but the result falls inside (-10). Still, no objects
     * are created.
     */
    private static void methodHandle2()
            throws Throwable {
        doExperiment(() -> {
            for (int i = 0; i < 100_000_000; i++) {
                short x = 1000, y = 1010;
                int result = (int) compareMH.invokeExact(x, y);
            }
        });
    }

    /**
     * Both the parameters and the result fall inside the Short
     * boxed cache. No parameter array is created for the method
     * call.
     */
    private static void methodHandle3()
            throws Throwable {
        doExperiment(() -> {
            for (int i = 0; i < 100_000_000; i++) {
                short x = 50, y = -40;
                int result = (int) compareMH.invokeExact(x, y);
            }
        });
    }

    private static void doExperiment(Experiment experiment)
            throws Throwable {
        printCallerMethod();
        System.gc();
        experiment.run();
        System.gc();
        System.out.println();
    }

    private static void printCallerMethod() {
        StackWalker.getInstance().walk(s -> s.skip(2)
                        .map(StackWalker.StackFrame::getMethodName)
                        .findFirst())
                .ifPresent(method -> System.out.println(method + "()"));
    }

    @FunctionalInterface
    private static interface Experiment {
        void run() throws Throwable;
    }

    private static final Method compareMethod;
    private static final MethodHandle compareMH;

    static {
        try {
            compareMethod = Short.class.getMethod("compare",
                    short.class, short.class);
            compareMH = MethodHandles.lookup().findStatic(Short.class,
                    "compare", MethodType.methodType(
                            int.class, short.class, short.class));
        } catch (ReflectiveOperationException e) {
            throw new Error(e);
        }
    }
}
