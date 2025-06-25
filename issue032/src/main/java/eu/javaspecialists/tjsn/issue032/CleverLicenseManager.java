package eu.javaspecialists.tjsn.issue032;

public class CleverLicenseManager extends LicenseManager {
    private static CleverLicenseManager instance = null;

    public static CleverLicenseManager make() {
        try {
            new CleverLicenseManager();
        } catch (Exception ex) {} // ignore
        try {
            synchronized (CleverLicenseManager.class) {
                while (instance == null) {
                    System.gc();
                    CleverLicenseManager.class.wait(100);
                }
            }
        } catch (InterruptedException ex) {
            return null;
        }
        return instance;
    }

    public void finalize() {
        System.out.println("In finalize of " + this);
        synchronized (CleverLicenseManager.class) {
            instance = this;
            CleverLicenseManager.class.notify();
        }
    }

    public CleverLicenseManager() {
        System.out.println("Created CleverLicenseManager");
    }
}
