package eu.javaspecialists.tjsn.issue029;

import java.io.*;
import java.util.*;

public class CircularArrayList extends AbstractList
        implements List, Serializable {
    private Object[] elementData;
    private int head = 0, tail = 0;
    private int size = 0;

    public CircularArrayList() {
        this(10);
    }

    public CircularArrayList(int size) {
        elementData = new Object[size];
    }

    public CircularArrayList(Collection c) {
        tail = c.size();
        elementData = new Object[c.size()];
        c.toArray(elementData);
    }

    private int convert(int index) {
        return (index + head) % elementData.length;
    }

    public boolean isEmpty() {
        return head == tail;
    }

    public void ensureCapacity(int minCapacity) {
        int oldCapacity = elementData.length;
        if (minCapacity > oldCapacity) {
            int newCapacity = (oldCapacity * 3) / 2 + 1;
            if (newCapacity < minCapacity)
                newCapacity = minCapacity;
            Object newData[] = new Object[newCapacity];
            toArray(newData);
            tail = size;
            head = 0;
            elementData = newData;
        }
    }

    public int size() {
        // the size can also be worked out each time as:
        // (tail + elementData.length - head) % elementData.length
        return size;
    }

    public boolean contains(Object elem) {
        return indexOf(elem) >= 0;
    }

    public int indexOf(Object elem) {
        if (elem == null) {
            for (int i = 0; i < size; i++)
                if (elementData[convert(i)] == null)
                    return i;
        } else {
            for (int i = 0; i < size; i++)
                if (elem.equals(elementData[convert(i)]))
                    return i;
        }
        return -1;
    }

    public int lastIndexOf(Object elem) {
        if (elem == null) {
            for (int i = size - 1; i >= 0; i--)
                if (elementData[convert(i)] == null)
                    return i;
        } else {
            for (int i = size - 1; i >= 0; i--)
                if (elem.equals(elementData[convert(i)]))
                    return i;
        }
        return -1;
    }

    public Object[] toArray() {
        return toArray(new Object[size]);
    }

    public Object[] toArray(Object a[]) {
        if (a.length < size)
            a = (Object[]) java.lang.reflect.Array.newInstance(
                    a.getClass().getComponentType(), size);
        if (head < tail) {
            System.arraycopy(elementData, head, a, 0, tail - head);
        } else {
            System.arraycopy(elementData, head, a, 0,
                    elementData.length - head);
            System.arraycopy(elementData, 0, a, elementData.length - head,
                    tail);
        }
        if (a.length > size)
            a[convert(size)] = null;
        return a;
    }

    private void rangeCheck(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", Size: " + size);
    }

    public Object get(int index) {
        rangeCheck(index);
        return elementData[convert(index)];
    }

    public Object set(int index, Object element) {
        modCount++;
        rangeCheck(index);
        Object oldValue = elementData[convert(index)];
        elementData[convert(index)] = element;
        return oldValue;
    }

    public boolean add(Object o) {
        modCount++;
        ensureCapacity(size + 1 + 1);
        elementData[tail] = o;
        tail = (tail + 1) % elementData.length;
        size++;
        return true;
    }

    public Object remove(int index) {
        modCount++;
        rangeCheck(index);
        int pos = convert(index);
        // an interesting application of try/finally is to avoid
        // having to use local variables
        try {
            return elementData[pos];
        } finally {
            elementData[pos] = null; // Let gc do its work
            // optimized for FIFO access, i.e. adding to back and
            // removing from front
            if (pos == head) {
                head = (head + 1) % elementData.length;
            } else if (pos == tail) {
                tail = (tail - 1 + elementData.length) % elementData.length;
            } else {
                if (pos > head && pos > tail) { // tail/head/pos
                    System.arraycopy(elementData, head, elementData, head + 1,
                            pos - head);
                    head = (head + 1) % elementData.length;
                } else {
                    System.arraycopy(elementData, pos + 1, elementData, pos,
                            tail - pos - 1);
                    tail = (tail - 1 + elementData.length) % elementData.length;
                }
            }
            size--;
        }
    }

    public void clear() {
        modCount++;
        // Let gc do its work
        for (int i = head; i != tail; i = (i + 1) % elementData.length)
            elementData[i] = null;
        head = tail = size = 0;
    }

    public boolean addAll(Collection c) {
        modCount++;
        int numNew = c.size();
        ensureCapacity(size + numNew + 1);
        Iterator e = c.iterator();
        for (int i = 0; i < numNew; i++) {
            elementData[tail] = e.next();
            tail = (tail + 1) % elementData.length;
            size++;
        }
        return numNew != 0;
    }

    public void add(int index, Object element) {
        throw new UnsupportedOperationException(
                "This method left as an exercise to the reader ;-)");
    }

    public boolean addAll(int index, Collection c) {
        throw new UnsupportedOperationException(
                "This method left as an exercise to the reader ;-)");
    }

    private synchronized void writeObject(ObjectOutputStream s)
            throws IOException {
        s.writeInt(size);
        for (int i = head; i != tail; i = (i + 1) % elementData.length)
            s.writeObject(elementData[i]);
    }

    private synchronized void readObject(ObjectInputStream s)
            throws IOException, ClassNotFoundException {
        // Read in size of list and allocate array
        head = 0;
        size = tail = s.readInt();
        elementData = new Object[tail];
        // Read in all elements in the proper order.
        for (int i = 0; i < tail; i++)
            elementData[i] = s.readObject();
    }
}
