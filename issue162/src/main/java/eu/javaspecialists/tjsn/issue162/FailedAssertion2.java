package eu.javaspecialists.tjsn.issue162;

import java.util.*;
import java.util.concurrent.*;

public class FailedAssertion2 {
    public static <T> List<T> makeRandomList() {
        switch ((int) (Math.random() * 3)) {
            case 0:
                return new ArrayList<T>();
            case 1:
                return new LinkedList<T>();
            case 2:
                return new CopyOnWriteArrayList<T>();
            default:
                throw new AssertionError("Impossible case");
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(
                    makeRandomList().getClass().getSimpleName());
        }
    }
}
