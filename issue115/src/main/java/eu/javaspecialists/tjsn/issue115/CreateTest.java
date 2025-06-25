package eu.javaspecialists.tjsn.issue115;

public abstract class CreateTest {
    private long count;

    public long getCount() {
        return count;
    }

    protected final void incCount() {
        count++;
    }

    public abstract void run();
}