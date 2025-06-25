package eu.javaspecialists.tjsn.issue032;

public class Application {
    public static void main(String[] args) {
        LicenseManager lm;
        try {
            lm = new LicenseManager();
        } catch (SecurityException ex) {
            lm = null;
        }
        SecuritySystem.register(lm);
        System.out.println("Now let's get things started");
    }
}
