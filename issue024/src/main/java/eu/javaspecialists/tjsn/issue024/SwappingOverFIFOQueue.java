package eu.javaspecialists.tjsn.issue024;

import java.lang.ref.*;
import java.util.*;

public final class SwappingOverFIFOQueue implements FIFO {
    /**
     * The low value after which we switch over to ArrayList
     */
    private static int LOW = 30;
    /**
     * The high value after which we switch down to LinkedList
     */
    private static int HIGH = 70;
    /**
     * This list contains weak references of instances of this
     * class
     */
    private static List instances = new LinkedList();

    /** We add the weak references in an initializer block */ {
        instances.add(new WeakReference(this));
    }

    /**
     * When we set the low and high water marks we go through all
     * the existing instances and check for the optimal list type.
     * If the references is null we remove the WeakReference from
     * our instance list.
     */
    public static void setWaterMarks(int low, int high) {
        LOW = low;
        HIGH = high;
        Iterator it = instances.iterator();
        while (it.hasNext()) {
            WeakReference ref = (WeakReference) it.next();
            SwappingOverFIFOQueue q = (SwappingOverFIFOQueue) ref.get();
            if (q == null) {
                it.remove();
            } else {
                q.checkOptimalListType();
            }
        }
    }

    private List list = new ArrayList();

    public Class getListType() {
        return list.getClass();
    }

    private int size = 0;

    public boolean add(Object o) {
        try {
            list.add(o);
            return true;
        } finally {
            if (++size == HIGH) checkOptimalListType();
        }
    }

    public Object remove() {
        try {
            return list.remove(0);
        } finally {
            if (--size == LOW) checkOptimalListType();
        }
    }

    public int size() {
        return size;
    }

    private void checkOptimalListType() {
        if (size >= HIGH && (!(list instanceof LinkedList))) {
            list = new LinkedList(list);
        } else if (size <= LOW && (!(list instanceof ArrayList))) {
            list = new ArrayList(list);
        }
    }
}