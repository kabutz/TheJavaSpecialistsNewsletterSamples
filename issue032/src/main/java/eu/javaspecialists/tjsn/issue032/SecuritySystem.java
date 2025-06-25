package eu.javaspecialists.tjsn.issue032;

public class SecuritySystem {
    private static LicenseManager licenseManager;

    public static void register(LicenseManager lm) {
        // only register if it is not initialized
        if (licenseManager == null) {
            if (lm == null) {
                System.out.println("License Manager invalid!");
                System.exit(1);
            }
            licenseManager = lm;
        }
    }
}
