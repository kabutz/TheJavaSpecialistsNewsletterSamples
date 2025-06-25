package eu.javaspecialists.tjsn.issue117;

public class InsideJob {
    public static void run() {
        Exec.run(new Greeter() {
            public void hello() {
                System.out.println("Hello from InsideJob");
            }
        });
    }
}
