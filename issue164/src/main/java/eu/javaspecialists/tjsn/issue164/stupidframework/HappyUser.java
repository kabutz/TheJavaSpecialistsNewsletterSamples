package eu.javaspecialists.tjsn.issue164.stupidframework;

public class HappyUser extends StupidInhouseFramework {
    private final Long density;

    private static final ThreadLocal<Long> density_param =
            new ThreadLocal<Long>();

    private static String setParams(String title, long density) {
        density_param.set(density);
        return title;
    }

    private long getDensity() {
        Long param = density_param.get();
        if (param != null) {
            return param;
        }
        return density;
    }

    public HappyUser(String title, long density) {
        super(setParams(title, density));
        this.density = density;
        density_param.remove();
    }

    public void draw() {
        long density_fudge_value = getDensity() + 30 * 113;
        System.out.println("draw ... " + density_fudge_value);
    }

    public static void main(String... args) {
        StupidInhouseFramework sif = new HappyUser("Poor Me", 33244L);
        sif.draw();
    }
}
