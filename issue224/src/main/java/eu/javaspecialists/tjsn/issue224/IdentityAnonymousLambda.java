package eu.javaspecialists.tjsn.issue224;

public class IdentityAnonymousLambda {
    public static void main(String... args) {
        for (int i = 0; i < 2; i++) {
            showIdentity(() ->
                    System.out.println("Lambda - no fields"));
            showIdentity(() ->
                    System.out.println("Lambda - parameters - " + args));
            showIdentity(new Runnable() {
                public void run() {
                    System.out.println("anon - no fields");
                }
            });
            showIdentity(new Runnable() {
                public void run() {
                    System.out.println("anon - parameters - " + args);
                }
            });
            System.out.println();
        }
    }

    private static void showIdentity(Runnable runnable) {
        System.out.printf("%x ", System.identityHashCode(runnable));
        runnable.run();
    }
}
