package eu.javaspecialists.tjsn.issue170;

import java.lang.ref.*;
import java.lang.reflect.*;
import java.util.*;

public class FinalizerWatcher implements FinalizerWatcherMBean {
    private final Class<?> finalizerClazz;
    private final Object lock;
    private final Field unfinalizedField;
    private final Field nextField;
    private final Field referentField;

    public FinalizerWatcher() {
        try {
            finalizerClazz = Class.forName("java.lang.ref.Finalizer");

            // we need to lock on this field to avoid racing conditions
            Field lockField = finalizerClazz.getDeclaredField("lock");
            lockField.setAccessible(true);
            lock = lockField.get(null);

            // the start into the linked list of finalizers
            unfinalizedField = finalizerClazz.getDeclaredField(
                    "unfinalized");
            unfinalizedField.setAccessible(true);

            // the next element in the linked list
            nextField = finalizerClazz.getDeclaredField("next");
            nextField.setAccessible(true);

            // the object that the finalizer is defined on
            referentField = Reference.class.getDeclaredField("referent");
            referentField.setAccessible(true);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Could not create FinalizerWatcher", e);
        }
    }

    public int getNumberOfObjectsThatMightGetFinalizedOneDay() {
        class CountingVisitor implements Visitor {
            private int objectsToBeFinalized = 0;

            public void visit(Object value) {
                objectsToBeFinalized++;
            }
        }
        CountingVisitor visitor = new CountingVisitor();
        processAll(visitor);
        return visitor.objectsToBeFinalized;
    }

    private interface Visitor {
        public void visit(Object value) throws IllegalAccessException;
    }

    public void printObjectsThatMightGetFinalizedOneDay(
            final boolean detailed) {
        class PrintingVisitor implements Visitor {
            private int objectsToBeFinalized = 0;

            public void visit(Object value)
                    throws IllegalAccessException {
                System.out.println(value);
                if (detailed) showAllFieldValues(value);
                objectsToBeFinalized++;
            }
        }
        PrintingVisitor visitor = new PrintingVisitor();

        System.out.println("Objects registered for finalization");
        System.out.println("===================================");
        processAll(visitor);
        System.out.println("Found " + visitor.objectsToBeFinalized +
                " objects registered for finalization");
    }

    public void printUniqueClassesWithFinalizers() {
        class Counter {
            int value;
        }

        final Map<String, Counter> classes =
                new TreeMap<String, Counter>();

        class UniqueClassesVisitor implements Visitor {
            public void visit(Object value)
                    throws IllegalAccessException {
                String className = value.getClass().getName();
                Counter count = classes.get(className);
                if (count == null) count = new Counter();
                count.value++;
                classes.put(className, count);
            }
        }

        UniqueClassesVisitor visitor = new UniqueClassesVisitor();
        processAll(visitor);
        System.out.println("Unique Classes with Finalizers");
        System.out.println("==============================");
        for (Map.Entry<String, Counter> entry : classes.entrySet()) {
            System.out.printf("%d\t%s%n", entry.getValue().value,
                    entry.getKey());
        }
    }

    public void runFinalizers() {
        System.runFinalization();
    }

    public void collectGarbage() {
        System.gc();
    }

    private void processAll(Visitor visitor) {
        try {
            synchronized (lock) {
                Object finalizer = unfinalizedField.get(null);
                while (finalizer != null) {
                    Object value = referentField.get(finalizer);
                    visitor.visit(value);
                    finalizer = nextField.get(finalizer);
                }
            }
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    private void showAllFieldValues(Object value)
            throws IllegalAccessException {
        Class clazz = value.getClass();
        Collection<Field[]> allFields = new ArrayList<Field[]>();
        while (clazz != null) {
            allFields.add(clazz.getDeclaredFields());
            clazz = clazz.getSuperclass();
        }
        for (Field[] fields : allFields) {
            for (Field field : fields) {
                field.setAccessible(true);
                System.out.println("\t" + field.getName() + "=" +
                        field.get(value));
            }
        }
    }
}
