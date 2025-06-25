package eu.javaspecialists.tjsn.issue224;

public class Interruptions {
    public static void saveForLater(InterruptibleAction action) {
        saveForLater(action::run);
    }
    /* Previously
    public static void saveForLater(InterruptibleAction action) {
        saveForLater(new InterruptibleAction() {
            public void run() throws InterruptedException {
                action.run();
            }
        });
    }
     */

    public static <E> E saveForLater(
            InterruptibleTask<E> task) {
        boolean interrupted = Thread.interrupted(); // clears flag
        try {
            while (true) {
                try {
                    return task.run();
                } catch (InterruptedException e) {
                    // flag would be cleared at this point too
                    interrupted = true;
                }
            }
        } finally {
            if (interrupted) Thread.currentThread().interrupt();
        }
    }

    @FunctionalInterface
    public interface InterruptibleAction {
        public void run() throws InterruptedException;
    }

    @FunctionalInterface
    public interface InterruptibleTask<E> {
        public E run() throws InterruptedException;
    }
}
