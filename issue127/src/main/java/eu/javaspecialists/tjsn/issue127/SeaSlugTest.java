package eu.javaspecialists.tjsn.issue127;

public class SeaSlugTest {
    public static void main(String... args) throws Exception {
        Class<?> someClass = Class.forName("eu.javaspecialists.tjsn.issue127.SeaSlug");
        Class<? extends Alien> clz = someClass.asSubclass(Alien.class);
        Alien alien = clz.newInstance();
        alien.glow();
    }
}