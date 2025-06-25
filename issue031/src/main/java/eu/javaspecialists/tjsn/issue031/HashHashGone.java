package eu.javaspecialists.tjsn.issue031;

import java.lang.reflect.*;
import java.util.*;

public class HashHashGone {
    // Did you know that you can make a static final data member
    // without a value (i.e. blank), as long as you set the value
    // in the static initializer?  I love making every possible
    // field final as it smokes out a lot of bugs.
    private static final Field intValue;

    static {
        try {
            intValue = Integer.class.getDeclaredField("value");
            intValue.setAccessible(true);
        } catch (NoSuchFieldException ex) {
            // we throw a RuntimeException from the static initializer
            // this will actually generate an Error and kill the thread
            // that first accessed GhostSet.
            throw new RuntimeException(
                    "Serious error - no \"value\" field found in Integer");
        }
    }

    // This method changes the integer passed into the method to
    // the new value.
    private static void setInteger(Integer i, int newInt) {
        try {
            intValue.set(i, new Integer(newInt));
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(
                    "Serious error - field should have been accessible");
        }
    }

    // This method will be used later to print a detailed view of
    // the set, including the value, the class and the identity
    // hash code of the object.
    private static void printDetailed(Set set) {
        System.out.print("[");
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Object val = it.next();
            System.out.print("\t(toString()=" + val + ",class="
                    + val.getClass().getName() + ",identityHashCode="
                    + System.identityHashCode(val) + ")");
            if (it.hasNext()) System.out.print(", ");
            System.out.println();
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println(
                    "arguments: <initial-intvalue> <num-copies-to-insert-in-Set>");
            return;
        }
        int initialValue = Integer.parseInt(args[0]);
        int numberOfCopiesToInsert = Integer.parseInt(args[1]);

        Integer x = new Integer(initialValue);
        int currValue = initialValue;

        Set set = new HashSet();
        for (int i = 0; i < numberOfCopiesToInsert; i++) {
            setInteger(x, ++currValue);
            set.add(x);
        }
        setInteger(x, initialValue);
        System.out.println("here's a set containing " +
                numberOfCopiesToInsert + " copies of Integer(" + x + "): ");
        System.out.println(set);
        System.out.println("detailed view of set:");
        printDetailed(set);
        System.out.println("and does it contain that Integer?: " +
                set.contains(x));

        System.out.println("can the Integer be removed from the Set?");
        System.out.println(set.remove(x));
        System.out.println("the Set contents after attempted remove:");
        System.out.println(set);

        setInteger(x, -initialValue);
        System.out.println(
                "altering the Integer to its opposite makes the Set contain:");
        System.out.println(set);

        setInteger(x, initialValue);
        currValue = initialValue;
        for (int i = 0; i < numberOfCopiesToInsert; i++) {
            setInteger(x, ++currValue);
            set.remove(x);
        }
        System.out.println("now all the elements have been removed "
                + "from the Set as it contains:");
        System.out.println(set);
        System.out.println();
    }
}
