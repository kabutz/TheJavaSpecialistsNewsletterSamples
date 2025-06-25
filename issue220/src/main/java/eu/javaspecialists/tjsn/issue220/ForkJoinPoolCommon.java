package eu.javaspecialists.tjsn.issue220;

import java.util.concurrent.*;

public class ForkJoinPoolCommon {
    public static void main(String... args) {
        System.out.println(ForkJoinPool.commonPool());
    }
}
