package eu.javaspecialists.tjsn.issue233;

public class PsychiatristTraditional {
    public static void main(String... args) {
        Runnable job = new MultiplePersonalities().create();
        for (Class<?> clazz : job.getClass().getInterfaces()) {
            System.out.println(clazz.getName());
        }
    }
}
