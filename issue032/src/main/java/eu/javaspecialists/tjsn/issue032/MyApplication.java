package eu.javaspecialists.tjsn.issue032;

public class MyApplication {
    public static void main(String[] args) {
        MyLicenseManager lm;
        try {
            lm = new MyLicenseManager();
        } catch (SecurityException ex) {
            lm = null;
        }
        SecuritySystem.register(lm);
        // now we call the other application
        Application.main(args);
    }
}
