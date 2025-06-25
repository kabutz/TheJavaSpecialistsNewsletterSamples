package eu.javaspecialists.tjsn.issue117;

public class Main {
    public static void main(String[] args) {
        InsideJob.run();
        Exec.run(new Greeter() {
            public void hello() {
                System.out.println("Hello from Main");
            }
        });
    }
}
