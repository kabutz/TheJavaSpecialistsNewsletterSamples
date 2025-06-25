package eu.javaspecialists.tjsn.issue266;

public class Threading09 {
    static class Foo {
        public int x, y;
        public boolean f;

        public void bar() {
            x = 5;
            y = 10;
            f = true;
        }
    }

    public static void main(String... args) throws InterruptedException {
        Foo f = new Foo();

        Thread t1 = new Thread(f::bar);
        Thread t2 = new Thread(() -> {
            while (!f.f) {}
            assert (f.x == 5);
        });

        t2.start();
        Thread.sleep(10_000L);

        t1.start();

        t1.join();
        t2.join();
    }
}
