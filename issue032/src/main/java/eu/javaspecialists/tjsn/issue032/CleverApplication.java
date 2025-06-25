package eu.javaspecialists.tjsn.issue032;

public class CleverApplication {
    public static void main(String[] args) {
        CleverLicenseManager lm = CleverLicenseManager.make();
        SecuritySystem.register(lm);
        // now we call the other application
        Application.main(args);
    }
}
