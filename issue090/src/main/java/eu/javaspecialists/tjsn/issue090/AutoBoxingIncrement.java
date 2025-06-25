package eu.javaspecialists.tjsn.issue090;

import java.util.*;

public class AutoBoxingIncrement {
    public static void main(String... args) {
        // we set up a Collection containing Integers and an int[]
        List<Integer> values = new ArrayList<Integer>();
        int[] valuesArray = new int[1000];
        for (int i = 0; i < 1000; i++) {
            values.add(i);
            valuesArray[i] = i;
        }

        // let's time how quickly we can increment the 1000 values
        long time = System.currentTimeMillis();
        // we must do it a few times to see the difference
        for (int j = 0; j < 100000; j++) {
            for (int i = 0; i < values.size(); i++) {
                values.set(i, values.get(i) + 1);
            }
        }
        System.out.println("autoboxing with generics took " +
                (System.currentTimeMillis() - time) + "ms");

        // now we try with an array
        time = System.currentTimeMillis();
        for (int j = 0; j < 100000; j++) {
            for (int i = 0; i < valuesArray.length; i++) {
                valuesArray[i]++;
            }
        }
        System.out.println("Using a plain array took " +
                (System.currentTimeMillis() - time) + "ms");
    }
}
