package eu.javaspecialists.tjsn.issue025;

public class Access1 {
    public void f() {
        final int i = 3;
        Runnable runnable = new Runnable() {
            public void run() {
                System.out.println(i);
            }
        };
    }
}
