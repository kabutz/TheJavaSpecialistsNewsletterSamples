package eu.javaspecialists.tjsn.issue117;

public class Main2 {
    public static void main(String[] args) {
        InsideJob.run();
        Exec.run(new MyGreeter());
    }

    public static class MyGreeter extends Greeter {
        public void hello() {
            System.out.println("Hello from Main");
        }
    }
}
