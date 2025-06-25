package eu.javaspecialists.tjsn.issue188;

public class LockFreeInterlocker extends Interlocker {
    private volatile boolean evenHasNextTurn = true;

    private class Job implements Runnable {
        private final InterlockTask task;
        private final boolean even;

        private Job(InterlockTask task, boolean even) {
            this.task = task;
            this.even = even;
        }

        public void run() {
            while (!task.isDone()) {
                while (even ^ evenHasNextTurn) ;
                if (task.isDone()) {
                    return;
                }
                task.call();
                evenHasNextTurn = !even;
            }
        }
    }

    protected Runnable[] getRunnables(InterlockTask task) {
        return new Runnable[]{
                new Job(task, true), new Job(task, false)
        };
    }
}
