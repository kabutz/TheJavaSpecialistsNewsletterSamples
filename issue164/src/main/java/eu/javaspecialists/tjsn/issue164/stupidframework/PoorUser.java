package eu.javaspecialists.tjsn.issue164.stupidframework;

public class PoorUser extends StupidInhouseFramework {
    private final Long density;

    public PoorUser(String title, long density) {
        super(title);
        this.density = density;
    }

    public void draw() {
        long density_fudge_value = density + 30 * 113;
        System.out.println("draw ... " + density_fudge_value);
    }

    public static void main(String... args) {
        StupidInhouseFramework sif = new PoorUser("Poor Me", 33244L);
        sif.draw();
    }
}
