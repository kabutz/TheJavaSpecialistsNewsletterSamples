package eu.javaspecialists.tjsn.issue024;

import java.util.*;

/*
  For the sake of brevity we only consider two implementations
  of java.util.List, namely java.util.ArrayList and
  java.util.LinkedList. */
public class SwappingOverFIFOQueueProfiler {
    private static boolean isArrayListFaster(int entries) {
        System.out.println("isArrayListFaster(" + entries + ")");
        return countIterations(new ArrayList(), entries) > countIterations(new LinkedList(), entries);
    }

    private static int countIterations(List list, int entries) {
        for (int i = 0; i < entries; i++) {
            list.add(new Object());
        }
        long end = System.currentTimeMillis() + 1000;
        int iterations = 0;
        while (System.currentTimeMillis() <= end) {
            iterations++;
            list.add(new Object());
            list.remove(0);
        }
        return iterations;
    }

    static {
        int checks = 0;
        int watermark = 1;
        int bestWatermark = 0;
        for (int i = 0; i < 16; i++) {
            if (isArrayListFaster(watermark)) {
                bestWatermark = Math.max(watermark, bestWatermark);
                watermark *= 2.0;
            } else {
                watermark *= 0.75;
                if (watermark <= bestWatermark) watermark *= 1.25;
            }
        }
        System.out.println("Best watermark = " + bestWatermark);
        int low = (int) (bestWatermark * 0.75);
        int high = (int) (bestWatermark * 1.25);
        System.out.println("Setting LOW to " + low + " and HIGH to " + high);
        SwappingOverFIFOQueue.setWaterMarks(low, high);
    }

    public static void main(String[] args) {
        SwappingOverFIFOQueueTest.main(new String[]{"0"});
        SwappingOverFIFOQueueTest.main(new String[]{"100"});
        SwappingOverFIFOQueueTest.main(new String[]{"10000"});
    }
}
