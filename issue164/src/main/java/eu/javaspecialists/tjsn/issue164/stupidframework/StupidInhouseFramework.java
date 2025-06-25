package eu.javaspecialists.tjsn.issue164.stupidframework;

public abstract class StupidInhouseFramework {
    private final String title;

    protected StupidInhouseFramework(String title) {
        this.title = title;
        draw();
    }

    public abstract void draw();

    public String toString() {
        return "StupidInhouseFramework " + title;
    }
}
