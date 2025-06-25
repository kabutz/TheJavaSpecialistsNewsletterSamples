package eu.javaspecialists.tjsn.issue175;

import java.lang.reflect.*;

public class EnumCreation {
    public static void main(String... args) throws Exception {
        Thread.State fastAsleep =
                SilentObjectCreator.create(Thread.State.class);
        Field name = Enum.class.getDeclaredField("name");
        name.setAccessible(true);
        name.set(fastAsleep, "FAST_ASLEEP");
        System.out.println("fastAsleep = " + fastAsleep);
    }
}
