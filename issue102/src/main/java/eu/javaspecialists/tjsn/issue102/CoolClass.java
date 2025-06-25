package eu.javaspecialists.tjsn.issue102;

import java.lang.reflect.*;

public class CoolClass {
    static {
        try {
            Class[] classes = Integer.class.getDeclaredClasses();
            for (Class clazz : classes) {
                if (clazz.getName().endsWith("IntegerCache")) {
                    Field cacheField = clazz.getDeclaredField("cache");
                    cacheField.setAccessible(true);
                    Integer[] cache = (Integer[]) cacheField.get(null);
                    for (int i = 0; i < cache.length; i++) {
                        cache[i] = new Integer(0);
                    }
                }
            }
        } catch (Throwable e) {
            // we silently pretend we didn't want to destroy Java...
        }
    }

    public static void makeExcellentCuppaCoffee() {
        // let's pretend this function does something amazing,
        // like make a good cup of coffee
        System.out.println("Hmmm, the first cuppa in the morning is the best!");
    }
}
