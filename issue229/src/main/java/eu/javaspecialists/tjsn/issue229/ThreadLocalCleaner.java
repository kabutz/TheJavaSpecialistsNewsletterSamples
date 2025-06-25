package eu.javaspecialists.tjsn.issue229;


import java.lang.ref.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.*;

import static eu.javaspecialists.tjsn.issue229.ThreadLocalChangeListener.Mode.*;

public class ThreadLocalCleaner implements AutoCloseable {
    private final ThreadLocalChangeListener listener;

    public ThreadLocalCleaner() {
        this(ThreadLocalChangeListener.EMPTY);
    }

    public ThreadLocalCleaner(ThreadLocalChangeListener listener) {
        this.listener = listener;
        saveOldThreadLocals();
    }

    public void close() {
        cleanup();
    }

    public void cleanup() {
        diff(threadLocalsField, copyOfThreadLocals.get());
        diff(inheritableThreadLocalsField,
                copyOfInheritableThreadLocals.get());
        restoreOldThreadLocals();
    }

    public static void forEach(
            Thread thread,
            BiConsumer<ThreadLocal<?>, Object> consumer) {
        forEach(thread, threadLocalsField, consumer);
        forEach(thread, inheritableThreadLocalsField, consumer);
    }

    public static void cleanup(Thread thread) {
        try {
            threadLocalsField.set(thread, null);
            inheritableThreadLocalsField.set(thread, null);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(
                    "Could not clear thread locals: " + e);
        }
    }

    private void diff(Field field, Reference<?>[] backup) {
        try {
            Thread thread = Thread.currentThread();
            Object threadLocals = field.get(thread);
            if (threadLocals == null) {
                if (backup != null) {
                    for (Reference<?> reference : backup) {
                        changed(thread, reference,
                                REMOVED);
                    }
                }
                return;
            }

            Reference<?>[] current =
                    (Reference<?>[]) tableField.get(threadLocals);
            if (backup == null) {
                for (Reference<?> reference : current) {
                    changed(thread, reference, ADDED);
                }
            } else {
                // nested loop - both arrays *should* be relatively small
                next:
                for (Reference<?> curRef : current) {
                    if (curRef != null) {
                        if (curRef.get() == copyOfThreadLocals ||
                                curRef.get() == copyOfInheritableThreadLocals) {
                            continue next;
                        }
                        for (Reference<?> backupRef : backup) {
                            if (curRef == backupRef) continue next;
                        }
                        // could not find it in backup - added
                        changed(thread, curRef, ADDED);
                    }
                }
                next:
                for (Reference<?> backupRef : backup) {
                    for (Reference<?> curRef : current) {
                        if (curRef == backupRef) continue next;
                    }
                    // could not find it in current - removed
                    changed(thread, backupRef,
                            REMOVED);
                }
            }
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Access denied", e);
        }
    }

    private void changed(Thread thread, Reference<?> reference,
                         ThreadLocalChangeListener.Mode mode)
            throws IllegalAccessException {
        listener.changed(mode,
                thread, (ThreadLocal<?>) reference.get(),
                threadLocalEntryValueField.get(reference));
    }

    private static Field field(Class<?> c, String name)
            throws NoSuchFieldException {
        Field field = c.getDeclaredField(name);
        field.setAccessible(true);
        return field;
    }

    private static Class<?> inner(Class<?> clazz, String name) {
        for (Class<?> c : clazz.getDeclaredClasses()) {
            if (c.getSimpleName().equals(name)) {
                return c;
            }
        }
        throw new IllegalStateException(
                "Could not find inner class " + name + " in " + clazz);
    }

    private static void forEach(
            Thread thread, Field field,
            BiConsumer<ThreadLocal<?>, Object> consumer) {
        try {
            Object threadLocals = field.get(thread);
            if (threadLocals != null) {
                Reference<?>[] table = (Reference<?>[])
                        tableField.get(threadLocals);
                for (Reference<?> ref : table) {
                    if (ref != null) {
                        ThreadLocal<?> key = (ThreadLocal<?>) ref.get();
                        if (key != null) {
                            Object value = threadLocalEntryValueField.get(ref);
                            consumer.accept(key, value);
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    private static final ThreadLocal<Reference<?>[]>
            copyOfThreadLocals = new ThreadLocal<>();

    private static final ThreadLocal<Reference<?>[]>
            copyOfInheritableThreadLocals = new ThreadLocal<>();

    private static void saveOldThreadLocals() {
        copyOfThreadLocals.set(copy(threadLocalsField));
        copyOfInheritableThreadLocals.set(
                copy(inheritableThreadLocalsField));
    }

    private static Reference<?>[] copy(Field field) {
        try {
            Thread thread = Thread.currentThread();
            Object threadLocals = field.get(thread);
            if (threadLocals == null) return null;
            Reference<?>[] table =
                    (Reference<?>[]) tableField.get(threadLocals);
            return Arrays.copyOf(table, table.length);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Access denied", e);
        }
    }

    private static void restoreOldThreadLocals() {
        try {
            restore(inheritableThreadLocalsField,
                    copyOfInheritableThreadLocals.get());
            restore(threadLocalsField, copyOfThreadLocals.get());
        } finally {
            copyOfThreadLocals.remove();
            copyOfInheritableThreadLocals.remove();
        }
    }

    private static void restore(Field field, Object value) {
        try {
            Thread thread = Thread.currentThread();
            if (value == null) {
                field.set(thread, null);
            } else {
                tableField.set(field.get(thread), value);
            }
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Access denied", e);
        }
    }

    /* Reflection fields */
    private static final Field threadLocalsField;

    private static final Field inheritableThreadLocalsField;
    private static final Class<?> threadLocalMapClass;
    private static final Field tableField;
    private static final Class<?> threadLocalMapEntryClass;

    private static final Field threadLocalEntryValueField;

    static {
        try {
            threadLocalsField = field(Thread.class, "threadLocals");
            inheritableThreadLocalsField =
                    field(Thread.class, "inheritableThreadLocals");

            threadLocalMapClass =
                    inner(ThreadLocal.class, "ThreadLocalMap");

            tableField = field(threadLocalMapClass, "table");
            threadLocalMapEntryClass =
                    inner(threadLocalMapClass, "Entry");

            threadLocalEntryValueField =
                    field(threadLocalMapEntryClass, "value");
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException(
                    "Could not locate threadLocals field in Thread.  " +
                            "Will not be able to clear thread locals: " + e);
        }
    }
}
