package eu.javaspecialists.tjsn.issue032;

public class LicenseManager {
    public LicenseManager() {
        if (!cleverLicenseValidation()) {
            throw new SecurityException("License invalid");
        }
    }

    private boolean cleverLicenseValidation() {
        // here you would typically read the license
        // file, do some obfuscated calculations, etc.
        // and return true if the license is valid.
        return false;
    }
}
