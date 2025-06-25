package eu.javaspecialists.tjsn.issue181;

import eu.javaspecialists.tjsn.issue180.*;

import java.lang.reflect.*;
import java.util.*;

public class ProxyGenerator {
    private static final WeakHashMap cache = new WeakHashMap();

    public static <T> T make(
            Class<T> subject,
            Class<? extends T> realClass,
            Concurrency concurrency,
            ProxyType type) {
        return make(subject.getClassLoader(),
                subject, realClass, concurrency, type);
    }

    public static <T> T make(
            Class<T> subject, Class<? extends T> realClass,
            Concurrency concurrency) {
        return make(subject, realClass, concurrency,
                ProxyType.STATIC);
    }

    public static <T> T make(ClassLoader loader,
                             Class<T> subject,
                             Class<? extends T> realClass,
                             Concurrency concurrency,
                             ProxyType type) {

        Object proxy = null;
        if (type == ProxyType.STATIC) {
            proxy = createStaticProxy(loader, subject,
                    realClass, concurrency);
        } else if (type == ProxyType.DYNAMIC) {
            proxy = createDynamicProxy(loader,
                    subject, realClass, concurrency);
        }
        return subject.cast(proxy);
    }

    private static Object createStaticProxy(
            ClassLoader loader, Class subject,
            Class realClass, Concurrency concurrency) {
        Map clcache;
        synchronized (cache) {
            clcache = (Map) cache.get(loader);
            if (clcache == null) {
                cache.put(loader, clcache = new HashMap());
            }
        }

        try {
            Class clazz;
            CacheKey key = new CacheKey(subject, concurrency);
            synchronized (clcache) {
                clazz = (Class) clcache.get(key);
                if (clazz == null) {
                    VirtualProxySourceGenerator vpsg = create(subject,
                            realClass, concurrency);
                    clazz = Generator.make(loader, vpsg.getProxyName(),
                            vpsg.getCharSequence());
                    clcache.put(key, clazz);
                }
            }
            return clazz.newInstance();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static VirtualProxySourceGenerator create(
            Class subject, Class realClass,
            Concurrency concurrency) {
        switch (concurrency) {
            case NONE:
                return new VirtualProxySourceGeneratorNotThreadsafe(
                        subject, realClass
                );
            case SOME_DUPLICATES:
                return new VirtualProxySourceGeneratorSomeDuplicates(
                        subject, realClass
                );
            case NO_DUPLICATES:
                return new VirtualProxySourceGeneratorNoDuplicates(
                        subject, realClass
                );
            default:
                throw new IllegalArgumentException(
                        "Unsupported Concurrency: " + concurrency);
        }
    }

    private static Object createDynamicProxy(
            ClassLoader loader, Class subject,
            Class realClass, Concurrency concurrency) {
        if (concurrency != Concurrency.NONE) {
            throw new IllegalArgumentException(
                    "Unsupported Concurrency: " + concurrency);
        }
        return Proxy.newProxyInstance(
                loader,
                new Class<?>[]{subject},
                new VirtualDynamicProxyNotThreadSafe(realClass));
    }

    private static class CacheKey {
        private final Class subject;
        private final Concurrency concurrency;

        private CacheKey(Class subject, Concurrency concurrency) {
            this.subject = subject;
            this.concurrency = concurrency;
        }

        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CacheKey that = (CacheKey) o;
            if (concurrency != that.concurrency) return false;
            return subject.equals(that.subject);
        }

        public int hashCode() {
            return 31 * subject.hashCode() + concurrency.hashCode();
        }
    }
}
